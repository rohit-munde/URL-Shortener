package com.example.url_shortener.dto;


import jakarta.validation.constraints.NotBlank;

public class CreateShortUrlDTO {

    @NotBlank(message = "Original URL must not be blank")
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
