package com.example.url_shortener.repository;

import com.example.url_shortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    UrlEntity findByShortCode(String shortCode);
}
