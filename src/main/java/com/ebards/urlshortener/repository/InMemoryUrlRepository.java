package com.ebards.urlshortener.repository;

import com.ebards.urlshortener.model.UrlMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
@Primary
public class InMemoryUrlRepository implements UrlRepository {
    private final Map<String, UrlMapping> urlInMemoryStorage = new HashMap<>();

    @Override
    public void save(UrlMapping urlMapping) {
        urlInMemoryStorage.put(urlMapping.getCode(), urlMapping);
        log.info("URL mapping has been saved into the in-memory storage. Here's the URL: {}", urlMapping.getOriginalUrl());
    }

    @Override
    public UrlMapping findByCode(String code) {
        log.info("Retrieving original URL by code {} from in-memory storage", code);
        return urlInMemoryStorage.get(code);
    }
}
