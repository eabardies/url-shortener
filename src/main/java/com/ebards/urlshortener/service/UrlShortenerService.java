package com.ebards.urlshortener.service;

public interface UrlShortenerService {
    String shortenUrl(String originalUrl);
    String getOriginalUrl(String code);
}
