package ru.geekbrains.vklimovich.springmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.geekbrains.vklimovich.springmvc.domain.Category;
import ru.geekbrains.vklimovich.springmvc.dto.CategoryDto;
import ru.geekbrains.vklimovich.springmvc.repository.CategoryRepository;
import ru.geekbrains.vklimovich.springmvc.service.CategoryService;
import ru.geekbrains.vklimovich.springmvc.service.exception.CategoryCannotDeleteException;
import ru.geekbrains.vklimovich.springmvc.service.exception.CategoryNotFoundException;
import ru.geekbrains.vklimovich.springmvc.service.exception.ProductNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("categoryService")
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(CategoryServiceImpl::newCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return newCategoryDto(findCategoryById(id));
    }

    @Override
    public Category findCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(!optionalCategory.isPresent())
            throw new CategoryNotFoundException(String.format("Category with ID=%d doesn't exist", id));
        return optionalCategory.get();
    }

    @Override
    public Category getCategoryByTitle(String title) {
        Optional<Category> optionalCategory = categoryRepository.findByTitle(title.toUpperCase());
        if(!optionalCategory.isPresent())
            throw new CategoryNotFoundException(String.format("Category %s not found", title));
        return optionalCategory.get();
    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Category category;
        Optional<Category> optionalCategory;
        if(categoryDto.getId() == null)
            category = new Category(categoryDto.getTitle());
        else{
            optionalCategory = categoryRepository.findById(categoryDto.getId());
            if(!optionalCategory.isPresent())
                throw new ProductNotFoundException(String.format("Category with ID=%d doesn't exist", categoryDto.getId()));
            category = optionalCategory.get();
            category.setTitle(categoryDto.getTitle());
        }
        System.out.println("Category: " + category);

        category = categoryRepository.save(category);
        System.out.println("Product saved: " + category);
        return newCategoryDto(category);
    }

    @Override
    public boolean removeCategory(Long id) {
        try{
            categoryRepository.deleteById(id);
            return true;
        }
        catch(EmptyResultDataAccessException e){
            CategoryNotFoundException exc = new CategoryNotFoundException(String.format("Category with ID=%d doesn't exist", id));
            exc.initCause(e);
            throw exc;
        }
        catch(DataIntegrityViolationException e){
            CategoryCannotDeleteException exc = new CategoryCannotDeleteException(String.format("Cannot remove the category with ID=%d cause it has linked products", id));
            exc.initCause(e);
            throw exc;
        }
    }

    private static CategoryDto newCategoryDto(Category c){
        return new CategoryDto(c.getId(), c.getTitle());
    }
}
