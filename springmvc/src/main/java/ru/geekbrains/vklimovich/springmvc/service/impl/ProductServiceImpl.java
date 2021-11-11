package ru.geekbrains.vklimovich.springmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.vklimovich.springmvc.domain.Category;
import ru.geekbrains.vklimovich.springmvc.domain.Product;
import ru.geekbrains.vklimovich.springmvc.dto.CategoryDto;
import ru.geekbrains.vklimovich.springmvc.dto.ProductDto;
import ru.geekbrains.vklimovich.springmvc.repository.ProductRepository;
import ru.geekbrains.vklimovich.springmvc.repository.specification.ProductSpecification;
import ru.geekbrains.vklimovich.springmvc.service.CartService;
import ru.geekbrains.vklimovich.springmvc.service.CategoryService;
import ru.geekbrains.vklimovich.springmvc.service.ProductService;
import ru.geekbrains.vklimovich.springmvc.service.exception.ProductNotFoundException;
import ru.geekbrains.vklimovich.springmvc.util.FileUtil;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("productService")
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CartService cartService;

    @Override
    @Transactional
    public List<ProductDto> getProductCatalog(String categoryTitle, BigDecimal maxPrice, BigDecimal minPrice, String nameRegex) {
        System.out.printf("Category = %s, maxPrice = %f, minPrice = %f, titleRegex = %s%n", categoryTitle, maxPrice, minPrice, nameRegex);
        Category category = null;
        if(categoryTitle != null && !categoryTitle.isEmpty()){
            category = categoryService.getCategoryByTitle(categoryTitle);
        }

        return productRepository.findAll(ProductSpecification.productFilter(category, maxPrice, minPrice, nameRegex))
                                 .stream()
                                  .map(ProductServiceImpl::newProductDto)
                                   .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductCatalog() {
        return productRepository.findAll()
                                 .stream()
                                  .map(ProductServiceImpl::newProductDto)
                                   .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent())
            throw new ProductNotFoundException(String.format("Product with ID = %d doesn't exist", id));
        return newProductDto(optionalProduct.get());
    }

    @Override
    @Transactional
    public ProductDto saveProduct(ProductDto prodDto) {
        return saveWithImage(prodDto, null);
    }

    @Override
    @Transactional
    public ProductDto saveWithImage(ProductDto prodDto, MultipartFile image) {
        Product product;
        Optional<Product> optionalProduct;
        System.out.println("saveWithImage");
        Category category = categoryService.findCategoryById(prodDto.getCategory().getId());
        if(prodDto.getId() == null)
            product = new Product(prodDto.getTitle(), prodDto.getCost(), category);
        else{
            optionalProduct = productRepository.findById(prodDto.getId());
            if(!optionalProduct.isPresent())
                throw new ProductNotFoundException(String.format("Product with ID=%d doesn't exist", prodDto.getId()));
            product = optionalProduct.get();
            product.setCategory(category);
            product.setCost(prodDto.getCost());
            product.setTitle(prodDto.getTitle());
        }
        System.out.println("Product: " + product);

        product = productRepository.save(product);
        System.out.println("Product saved: " + product);

        if (image != null && !image.isEmpty()) {
            Path pathImage = FileUtil.saveProductImage(image);
            product.setImagePath(pathImage.toString());

            product = productRepository.save(product);
            System.out.println("Product saved: " + product);
        }

        return newProductDto(product);
    }

    @Override
    public boolean removeProduct(Long id) {
        try{
            productRepository.deleteById(id);
            return true;
        }
        catch(EmptyResultDataAccessException e){
            ProductNotFoundException exc = new ProductNotFoundException(String.format("Product with ID=%d doesn't exist", id));
            exc.initCause(e);
            throw exc;
        }
    }

    private static ProductDto newProductDto(Product p){
        CategoryDto categoryDto = new CategoryDto(p.getCategory().getId(), p.getCategory().getTitle());
        return new ProductDto(p.getId(), p.getTitle(), p.getCost(), categoryDto, p.getImagePath());
    }
}
