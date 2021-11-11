package ru.geekbrains.vklimovich.springmvc.service;

import ru.geekbrains.vklimovich.springmvc.dto.ProductDto;
import ru.geekbrains.vklimovich.springmvc.service.exception.CartIllegalCountException;
import java.util.*;

public interface CartService {
    void addProduct(Long productId, int num) throws CartIllegalCountException;
    void removeProduct(Long productId, int num) throws CartIllegalCountException;
    Map<Long, Integer> getProducts();
    int getProductAmount(Long productId);
}
