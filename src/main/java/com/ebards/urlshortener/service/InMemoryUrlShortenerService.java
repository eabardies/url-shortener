package com.ebards.urlshortener.service;

import com.ebards.urlshortener.model.UrlMapping;
import com.ebards.urlshortener.repository.InMemoryUrlRepository;
import com.ebards.urlshortener.repository.RedisUrlRepository;
import com.ebards.urlshortener.repository.UrlRepository;
import com.ebards.urlshortener.util.UrlValidatorUtil;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class InMemoryUrlShortenerService implements UrlShortenerService {

    private final UrlRepository urlRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public InMemoryUrlShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public String shortenUrl(String originalUrl) {
        if (!UrlValidatorUtil.isValidUrl(originalUrl)) {
            throw new IllegalArgumentException("Invalid URL format.");
        }
        String code = generateCode();
        urlRepository.save(new UrlMapping(code, originalUrl));
        return "https://short.ly/" + code;
    }

    @Override
    public String getOriginalUrl(String code) {
        UrlMapping urlMapping = urlRepository.findByCode(code);
        if (urlMapping == null) {
            throw new IllegalArgumentException("URL not found.");
        }
        return urlMapping.getOriginalUrl();
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder();
        String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < 6; i++) {
            sb.append(BASE62.charAt(secureRandom.nextInt(BASE62.length())));
        }
        return sb.toString();
    }

}
