package ru.geekbrains.vklimovich.springmvc.service;

import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.vklimovich.springmvc.dto.ProductDto;

import java.math.BigDecimal;
import java.util.*;

public interface ProductService {
    List<ProductDto> getProductCatalog(String categoryTitle, BigDecimal maxPrice, BigDecimal minPrice, String nameRegex);
    List<ProductDto> getProductCatalog();
    ProductDto getProduct(Long id);
    ProductDto saveProduct(ProductDto product);
    ProductDto saveWithImage(ProductDto prodDto, MultipartFile image);
    boolean removeProduct(Long id);
}
