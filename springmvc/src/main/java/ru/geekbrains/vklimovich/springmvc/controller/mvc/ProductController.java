package ru.geekbrains.vklimovich.springmvc.controller.mvc;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.vklimovich.springmvc.dto.ProductDto;
import ru.geekbrains.vklimovich.springmvc.service.CategoryService;
import ru.geekbrains.vklimovich.springmvc.service.ProductService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final Validator validator;

    @GetMapping
    public String getProductList(Model model,
                                 @RequestParam(name="category", required = false) String category,
                                 @RequestParam(name="maxprice", required = false) BigDecimal maxPrice,
                                 @RequestParam(name="minprice", required = false) BigDecimal minPrice,
                                 @RequestParam(name="title", required = false) String nameRegex){
        List<ProductDto> products = productService.getProductCatalog(category, maxPrice, minPrice, nameRegex);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", products);
        return "product/catalog";
    }

    @GetMapping("/product")
    public String getNewProductForm(@RequestParam(name="id", required = false) Long id, Model model){
        model.addAttribute("categories", categoryService.findAll());
        if(id == null){
            model.addAttribute("product", new ProductDto());
        }
        else{
            ProductDto product = productService.getProduct(id);
            model.addAttribute("product", product);
        }
        return "product/product";
    }

    @PostMapping("/product")
    public RedirectView saveProduct(@ModelAttribute ProductDto product,
                                    @RequestParam(value = "image", required = false) MultipartFile image,
                                    RedirectAttributes attributes) {
        System.out.println("saveProduct " + product);
        System.out.println("image " + image);

        Set<ConstraintViolation<ProductDto>> violationSet = validator.validate(product);
        if (CollectionUtils.isNotEmpty(violationSet)) {
            String violations = violationSet.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));

            attributes.addFlashAttribute("violations", violations);

            return new RedirectView("/product");
        }

        System.out.println("productService.saveProduct");
        productService.saveWithImage(product, image);

        return new RedirectView("");
    }

    @GetMapping("/remove")
    public RedirectView removeProduct(@RequestParam(name="id", required = true) Long id,
                                      Model model,
                                      RedirectAttributes attributes){
        productService.removeProduct(id);
        return new RedirectView("");
    }

}
