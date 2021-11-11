package ru.geekbrains.vklimovich.springmvc.service.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(){
        super();
    }

    public CategoryNotFoundException(String message){
        super(message);
    }

    public CategoryNotFoundException(Exception cause){
        super(cause);
    }
}
