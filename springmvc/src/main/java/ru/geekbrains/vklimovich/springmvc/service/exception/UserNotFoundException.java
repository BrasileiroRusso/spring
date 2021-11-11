package ru.geekbrains.vklimovich.springmvc.service.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super();
    }

    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(Exception cause){
        super(cause);
    }
}
