package com.ebards.urlshortener.util;

import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Slf4j
public class UrlValidatorUtil {
    public static boolean isValidUrl(String url) {
        try {
            log.info("Validating URL {}", url);
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            log.error("URL {} is invalid.", url);
            return false;
        }
    }
}
