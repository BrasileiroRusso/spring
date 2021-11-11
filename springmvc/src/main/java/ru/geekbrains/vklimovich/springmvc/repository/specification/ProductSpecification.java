package ru.geekbrains.vklimovich.springmvc.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.vklimovich.springmvc.domain.Category;
import ru.geekbrains.vklimovich.springmvc.domain.Product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> productFilter(Category category, BigDecimal maxPrice, BigDecimal minPrice, String titleRegex){
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(category != null)
                    predicates.add(criteriaBuilder.equal(root.get("category"), category));
                if(maxPrice != null)
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxPrice));
                if(minPrice != null)
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minPrice));
                if(titleRegex != null && !titleRegex.isEmpty())
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + titleRegex + "%"));
                return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
            }
        };
    }
}
