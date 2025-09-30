package com.ebards.urlshortener.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlValidatorUtil {
    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
