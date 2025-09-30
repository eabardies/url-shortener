package com.ebards.urlshortener.repository;

import com.ebards.urlshortener.model.UrlMapping;

public interface UrlRepository {
    void save(UrlMapping urlMapping);
    UrlMapping findByCode(String code);
}
