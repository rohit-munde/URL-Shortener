package com.example.url_shortener.controller;

import com.example.url_shortener.dto.CreateShortUrlDTO;
import com.example.url_shortener.dto.ShortUrlResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    @PostMapping("shorten")
    public ShortUrlResponse createShortUrl(@RequestBody CreateShortUrlDTO createShortUrlDTO) {
        return new ShortUrlResponse("http://short.url/abc123");
    }

}
