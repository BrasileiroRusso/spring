package ru.geekbrains.vklimovich.springmvc.service.exception;

public class CartIllegalCountException extends RuntimeException {
    public CartIllegalCountException(){

    }

    public CartIllegalCountException(String message){
        super(message);
    }

    public CartIllegalCountException(Throwable cause){
        super(cause);
    }
}
