package com.example.url_shortener.dto;

import jakarta.validation.constraints.NotNull;

public class GetOriginalUrlDto {

    @NotNull(message = "Short code must not be null")
    private String shortCode;

    public String getShortCode() {
        return shortCode;
    }
}
