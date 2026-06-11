package com.example.url_shortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.url_shortener.repository.UrlRepository;

@Service
public class ShortUrlService {
    //pass this DTO to service
    // Service will generate an unique code
    //store that code along with the original URL in the DB
    // return the short url combined baseURL+Code

    @Autowired
    private UrlRepository urlRepository;

    private String generateUniqueCode() {
        // Implement logic to generate a unique code
        // This can be a random string or based on a sequence
        return "uniqueCode"; // Placeholder - replace with actual unique code generation logic
    }


}
