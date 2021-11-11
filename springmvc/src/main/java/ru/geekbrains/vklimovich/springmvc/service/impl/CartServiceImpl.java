package ru.geekbrains.vklimovich.springmvc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.geekbrains.vklimovich.springmvc.dto.ProductDto;
import ru.geekbrains.vklimovich.springmvc.service.CartService;
import ru.geekbrains.vklimovich.springmvc.service.exception.CartIllegalCountException;

import java.util.*;

@Service("cartService")
@SessionScope
public class CartServiceImpl implements CartService {
    private final Map<Long, Integer> products;

    public CartServiceImpl(){
        products = new HashMap<>();
        System.out.println("New cart");
    }

    @Override
    public void addProduct(Long productId, int num) throws CartIllegalCountException {
        Objects.requireNonNull(productId);
        if(num < 0)
            throw new CartIllegalCountException("Negative product units count!");
        System.out.println("Add to cart: productId = " + productId);
        products.put(productId, products.getOrDefault(productId, 0) + num);
        System.out.println("Cart: " + products);
    }

    @Override
    public void removeProduct(Long productId, int num) throws CartIllegalCountException {
        int curNum = getProductAmount(productId);
        curNum -= num;
        if(curNum < 0)
            throw new CartIllegalCountException("Too many product units to remove!");
        if(curNum == 0)
            products.remove(productId);
        else
            products.put(productId, curNum);
    }

    @Override
    public Map<Long, Integer> getProducts(){
        return Collections.unmodifiableMap(new HashMap<>(products));
    }

    @Override
    public int getProductAmount(Long productId){
        return products.getOrDefault(productId, 0);
    };

    @Override
    public String toString() {
        return products.toString();
    }
}
