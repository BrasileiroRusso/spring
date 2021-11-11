package ru.geekbrains.vklimovich.springmvc.service;

import ru.geekbrains.vklimovich.springmvc.domain.Category;
import ru.geekbrains.vklimovich.springmvc.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
    CategoryDto getCategoryById(Long id);
    Category findCategoryById(Long id);
    Category getCategoryByTitle(String title);
    CategoryDto saveCategory(CategoryDto categoryDto);
    boolean removeCategory(Long id);
}
