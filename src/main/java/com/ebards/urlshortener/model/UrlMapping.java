package com.ebards.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlMapping {
    private String code;
    private String originalUrl;
    private String shortenedUrl;
}
