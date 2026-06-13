package com.example.url_shortener.controller;

import com.example.url_shortener.dto.CreateShortUrlDTO;
import com.example.url_shortener.payload.ShortUrlResponsePayload;
import com.example.url_shortener.response.ApiSuccessResponse;
import com.example.url_shortener.service.ShortUrlService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    private final ShortUrlService shortUrlService;

    public UrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping("shorten")
    public ResponseEntity<@NonNull ApiSuccessResponse<ShortUrlResponsePayload>> createShortUrl(@Valid @RequestBody CreateShortUrlDTO createShortUrlDTO) {
        ShortUrlResponsePayload payload = this.shortUrlService.createShortUrl(createShortUrlDTO.getOriginalUrl());
        return ResponseEntity.ok(new ApiSuccessResponse<ShortUrlResponsePayload>("URL shortened successfully", payload));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = this.shortUrlService.getOriginalUrl(shortCode);
        if (originalUrl != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(originalUrl.replace(" ", "%20")))
                    .build();
        }
        return ResponseEntity.notFound().build();
    }
}
