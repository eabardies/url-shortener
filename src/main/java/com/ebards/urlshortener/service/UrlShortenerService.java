package com.ebards.urlshortener.service;

import com.ebards.urlshortener.dtos.ShortenUrlRequest;
import com.ebards.urlshortener.model.UrlMapping;

public interface UrlShortenerService {
    UrlMapping shortenUrl(ShortenUrlRequest request);
    String getOriginalUrl(String code);
}
