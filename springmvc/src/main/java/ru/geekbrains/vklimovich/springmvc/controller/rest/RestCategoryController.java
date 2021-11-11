package ru.geekbrains.vklimovich.springmvc.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.vklimovich.springmvc.controller.rest.response.ErrorResponse;
import ru.geekbrains.vklimovich.springmvc.controller.rest.response.OKResponse;
import ru.geekbrains.vklimovich.springmvc.dto.CategoryDto;
import ru.geekbrains.vklimovich.springmvc.service.CategoryService;
import ru.geekbrains.vklimovich.springmvc.service.exception.CategoryCannotDeleteException;
import ru.geekbrains.vklimovich.springmvc.service.exception.CategoryNotFoundException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class RestCategoryController {
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategories(){
        return categoryService.findAll();
    }

    @GetMapping("/{category_id}")
    public CategoryDto getCategoryByID(@PathVariable(value="category_id") Long categoryID){
        return categoryService.getCategoryById(categoryID);
    }

    @PostMapping
    public ResponseEntity<OKResponse> addCategory(@RequestBody CategoryDto categoryDto) {
        System.out.println("Rest Category POST: " + categoryDto);
        categoryDto.setId(null);
        CategoryDto category = categoryService.saveCategory(categoryDto);
        return new ResponseEntity<>(new OKResponse(category.getId(), HttpStatus.CREATED.value(), System.currentTimeMillis()), HttpStatus.CREATED);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OKResponse> updateCategory(@RequestBody CategoryDto categoryDto) {
        System.out.println("Rest Category PUT: " + categoryDto);
        CategoryDto category = categoryService.saveCategory(categoryDto);
        return new ResponseEntity<>(new OKResponse(category.getId(), HttpStatus.OK.value(), System.currentTimeMillis()), HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<OKResponse> deleteCategoryByID(@PathVariable(value="category_id") Long categoryID){
        System.out.println("Rest Category DELETE: " + categoryID);
        categoryService.removeCategory(categoryID);
        return new ResponseEntity<>(new OKResponse(categoryID, HttpStatus.OK.value(), System.currentTimeMillis()), HttpStatus.OK);
    }

    @ExceptionHandler({CategoryNotFoundException.class, CategoryCannotDeleteException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        System.out.println("Rest Category ERROR");
        ErrorResponse ErrorResponse = new ErrorResponse();
        ErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        ErrorResponse.setMessage(e.getMessage());
        ErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(ErrorResponse, HttpStatus.NOT_FOUND);
    }
}
