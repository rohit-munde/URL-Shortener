package com.example.url_shortener.controller;

import com.example.url_shortener.dto.CreateShortUrlDTO;
import com.example.url_shortener.dto.ShortUrlResponse;
import com.example.url_shortener.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    private ShortUrlService shortUrlService;

    public UrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping("shorten")
    public ShortUrlResponse createShortUrl(@RequestBody CreateShortUrlDTO createShortUrlDTO) {
        return this.shortUrlService.createShortUrl(createShortUrlDTO.getOriginalUrl());
    }

}
