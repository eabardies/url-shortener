package com.ebards.urlshortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OriginalUrlResponse {
    private String originalUrl;
}
