package com.example.url_shortener.dto;


import jakarta.validation.constraints.NotBlank;

public class CreateShortUrlDTO {

    @NotBlank(message = "Original URL must not be blank")
    private String originalUrl;
}
