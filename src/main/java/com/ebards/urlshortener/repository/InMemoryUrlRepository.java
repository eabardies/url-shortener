package com.ebards.urlshortener.repository;

import com.ebards.urlshortener.model.UrlMapping;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUrlRepository implements UrlRepository {
    private final Map<String, UrlMapping> urlInMemoryStorage = new HashMap<>();

    @Override
    public void save(UrlMapping urlMapping) {
        urlInMemoryStorage.put(urlMapping.getCode(), urlMapping);
    }

    @Override
    public UrlMapping findByCode(String code) {
        return urlInMemoryStorage.get(code);
    }
}
