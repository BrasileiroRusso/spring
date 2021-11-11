package ru.geekbrains.vklimovich.springmvc.controller.rest.response;

public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    public ErrorResponse() {
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
