package com.ebards.urlshortener.controller;

import com.ebards.urlshortener.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody String originalUrl) {
        try {
            String shortUrl = urlShortenerService.shortenUrl(originalUrl);
            return ResponseEntity.ok(shortUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        try {
            String originalUrl = urlShortenerService.getOriginalUrl(code);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", originalUrl)
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
