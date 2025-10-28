package com.ebards.urlshortener.exceptions;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException() {
        super("URL not found. Provide a valid code.");
    }
}
