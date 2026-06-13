package com.example.url_shortener.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class CreateShortUrlDTO {

    @NotBlank(message = "Original URL must not be blank")
    @URL(message = "Invalid URL format")
    @Size(max = 2048, message = "Original URL must not exceed 2048 characters")
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
