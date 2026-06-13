package com.example.url_shortener.service;

import com.example.url_shortener.exception.UrlNotFoundException;
import com.example.url_shortener.payload.ShortUrlResponsePayload;
import com.example.url_shortener.entity.UrlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.url_shortener.repository.UrlRepository;
import org.springframework.util.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
//import org.apache.commons.codec.digest.DigestUtils;

@Service
public class ShortUrlService {
    private final StringRedisTemplate redisTemplate;

    @Autowired
    private final UrlRepository urlRepository;

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public ShortUrlService(StringRedisTemplate redisTemplate, UrlRepository urlRepository) {
        this.redisTemplate = redisTemplate;
        this.urlRepository = urlRepository;
    }

    private String generateUniqueCode(Long id) {
       if (id == 0) {
           return "0";
       }
        StringBuilder shortUrlCode = new StringBuilder();
        while (id > 0) {
            int remainder = (int) (id % 62);
            shortUrlCode.append(BASE62.charAt(remainder));
            id /= 62;
        }
        return shortUrlCode.reverse().toString();
    }

    public ShortUrlResponsePayload createShortUrl(String originalUrl){
        //check if the original URL already exists in the database
        String urlHash = DigestUtils.md5DigestAsHex(originalUrl.getBytes(StandardCharsets.UTF_8));
        UrlEntity existingEntity = urlRepository.findByUrlHash(urlHash);
        if (existingEntity != null) {
            return new ShortUrlResponsePayload("http://short.url/" + existingEntity.getShortUrlCode());
        }
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        urlEntity.setClickCount(0L);
        urlEntity.setUrlHash(urlHash);
        UrlEntity savedEntity = urlRepository.save(urlEntity);
        String shortUrlCode = generateUniqueCode(savedEntity.getId());
        savedEntity.setShortUrlCode(shortUrlCode);
        urlRepository.save(savedEntity);
        return new ShortUrlResponsePayload("http://short.url/" + savedEntity.getShortUrlCode());
    }

    public String getOriginalUrl(String shortCode) {
        //We should first check the cache and if its not present in cache then else below code should run
        String cacheKey = "url:" + shortCode;
        String cachedUrl = redisTemplate.opsForValue().get(cacheKey);

        if(cachedUrl != null) {
            return cachedUrl;
        }
        UrlEntity urlEntity = urlRepository.findByShortUrlCode(shortCode);
        if (urlEntity == null) {
            // Throwing the custom exception instead of returning null
            throw new UrlNotFoundException("Short URL code '" + shortCode + "' not found");
        }
        redisTemplate.opsForValue().set(cacheKey, urlEntity.getOriginalUrl());
        return urlEntity.getOriginalUrl().replace(" ","%20");
    }
}
