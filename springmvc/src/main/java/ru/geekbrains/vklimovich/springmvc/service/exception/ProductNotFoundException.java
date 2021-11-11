package ru.geekbrains.vklimovich.springmvc.service.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(){
        super();
    }

    public ProductNotFoundException(String message){
        super(message);
    }

    public ProductNotFoundException(Exception cause){
        super(cause);
    }
}
