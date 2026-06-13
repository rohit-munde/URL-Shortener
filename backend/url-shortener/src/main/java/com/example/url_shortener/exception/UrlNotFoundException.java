package com.example.url_shortener.exception;

import org.springframework.http.HttpStatus;

public class UrlNotFoundException extends BusinessException {
    public UrlNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public UrlNotFoundException() {
        super("URL not found", HttpStatus.NOT_FOUND);
    }
}
