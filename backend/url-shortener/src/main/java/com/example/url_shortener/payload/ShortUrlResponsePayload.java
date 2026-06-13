package com.example.url_shortener.payload;

public class ShortUrlResponsePayload {
    private String shortUrl;

    public ShortUrlResponsePayload(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
