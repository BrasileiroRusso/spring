package ru.geekbrains.vklimovich.springmvc.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.vklimovich.springmvc.controller.rest.response.ErrorResponse;
import ru.geekbrains.vklimovich.springmvc.controller.rest.response.OKResponse;
import ru.geekbrains.vklimovich.springmvc.dto.ProductDto;
import ru.geekbrains.vklimovich.springmvc.service.ProductService;
import ru.geekbrains.vklimovich.springmvc.service.exception.CategoryNotFoundException;
import ru.geekbrains.vklimovich.springmvc.service.exception.ProductNotFoundException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class RestProductControler {
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts(){
        return productService.getProductCatalog();
    }

    @GetMapping("/{product_id}")
    public ProductDto getProductByID(@PathVariable(value="product_id") Long productID){
        System.out.println("Rest GET: " + productID);
        return productService.getProduct(productID);
    }

    @PostMapping
    public ResponseEntity<OKResponse> addProduct(@RequestBody ProductDto productDto) {
        System.out.println("Rest POST: " + productDto);
        productDto.setId(null);
        ProductDto product = productService.saveProduct(productDto);
        return new ResponseEntity<>(new OKResponse(product.getId(), HttpStatus.CREATED.value(), System.currentTimeMillis()), HttpStatus.CREATED);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OKResponse> updateProduct(@RequestBody ProductDto productDto) {
        System.out.println("Rest PUT: " + productDto);
        ProductDto product = productService.saveProduct(productDto);
        return new ResponseEntity<>(new OKResponse(product.getId(), HttpStatus.OK.value(), System.currentTimeMillis()), HttpStatus.OK);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<OKResponse> deleteProductByID(@PathVariable(value="product_id") Long productID){
        System.out.println("Rest DELETE: " + productID);
        productService.removeProduct(productID);
        return new ResponseEntity<>(new OKResponse(productID, HttpStatus.OK.value(), System.currentTimeMillis()), HttpStatus.OK);
    }

    @ExceptionHandler({ProductNotFoundException.class, CategoryNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        System.out.println("Rest Product ERROR");
        ErrorResponse ErrorResponse = new ErrorResponse();
        ErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        ErrorResponse.setMessage(e.getMessage());
        ErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(ErrorResponse, HttpStatus.NOT_FOUND);
    }
}

