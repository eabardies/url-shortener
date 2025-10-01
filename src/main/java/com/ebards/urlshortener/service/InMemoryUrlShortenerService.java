package com.ebards.urlshortener.service;

import com.ebards.urlshortener.model.UrlMapping;
import com.ebards.urlshortener.repository.UrlRepository;
import com.ebards.urlshortener.util.UrlValidatorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Primary
public class InMemoryUrlShortenerService implements UrlShortenerService {

    @Value("${app.code-length:5}")
    private int codeLength;
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
        codeLength = codeLength == 0 ? 6 : codeLength;
        for (int i = 0; i < codeLength; i++) {
            sb.append(BASE62.charAt(secureRandom.nextInt(BASE62.length())));
        }
        return sb.toString();
    }

}
