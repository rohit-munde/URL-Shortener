package com.example.url_shortener.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "urls")
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2048, nullable = false)
    private String originalUrl;

    @Column(nullable = true)
    private String shortUrlCode;

    @Column
    private Long clickCount;

    @Column(length = 64, nullable = false, unique = true)
    private String urlHash;

    @Column(nullable = true, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    public UrlEntity() {
    }

    public UrlEntity(Long id, String originalUrl, String shortUrlCode, Long clickCount, LocalDateTime createdAt,
            LocalDateTime updatedAt, String urlHash) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortUrlCode = shortUrlCode;
        this.clickCount = clickCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.urlHash = urlHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrlCode() {
        return shortUrlCode;
    }

    public void setShortUrlCode(String shortUrl) {
        this.shortUrlCode = shortUrl;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrlHash() {
        return urlHash;
    }

    public void setUrlHash(String urlHash) {
        this.urlHash = urlHash;
    }
}
