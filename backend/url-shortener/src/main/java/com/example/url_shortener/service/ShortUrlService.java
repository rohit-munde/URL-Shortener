package com.example.url_shortener.service;

import com.example.url_shortener.dto.ShortUrlResponse;
import com.example.url_shortener.entity.UrlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.url_shortener.repository.UrlRepository;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
//import org.apache.commons.codec.digest.DigestUtils;

@Service
public class ShortUrlService {
    //pass this DTO to service
    // Service will generate an unique code
    //store that code along with the original URL in the DB
    // return the short url combined baseURL+Code

    @Autowired
    private UrlRepository urlRepository;

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

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

    public ShortUrlResponse createShortUrl(String originalUrl){
        //check if the original URL already exists in the database
        String urlHash = DigestUtils.md5DigestAsHex(originalUrl.getBytes(StandardCharsets.UTF_8));
        UrlEntity existingEntity = urlRepository.findByUrlHash(urlHash);
        if (existingEntity != null) {
            return new ShortUrlResponse("http://short.url/" + existingEntity.getShortUrlCode());
        }
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        urlEntity.setClickCount(0L);
        urlEntity.setUrlHash(urlHash);
        UrlEntity savedEntity = urlRepository.save(urlEntity);
        String shortUrlCode = generateUniqueCode(savedEntity.getId());
        savedEntity.setShortUrlCode(shortUrlCode);
        urlRepository.save(savedEntity);
        return new ShortUrlResponse("http://short.url/" + savedEntity.getShortUrlCode());
    }
}
