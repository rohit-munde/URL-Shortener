package com.example.url_shortener.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{
    private final HttpStatus status;

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getHttpErrorStatus() {
       return this.status;
    }
}
