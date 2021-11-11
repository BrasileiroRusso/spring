package ru.geekbrains.vklimovich.springmvc.dto;

import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private BigDecimal cost;
    private CategoryDto category;
    private String imagePath;

    public ProductDto(Long id, String title, BigDecimal cost, CategoryDto category, String imagePath){
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.category = category;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getImagePath() {
        return imagePath == null?"":imagePath.replace('\\', '/');
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "ProductDto{id=" + id + ", title=" + title + ", cost=" + cost + ", category=" + category.getTitle() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(title, that.title) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, category);
    }
}