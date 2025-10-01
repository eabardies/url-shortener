package com.ebards.urlshortener.controller;

import com.ebards.urlshortener.service.UrlShortenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody String originalUrl) {
        log.info("POST /api/shorten called with: {}", originalUrl);
        try {
            String shortUrl = urlShortenerService.shortenUrl(originalUrl);
            log.info("Short URL: {}", shortUrl);
            return ResponseEntity.ok(shortUrl);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        log.info("GET /api/ called with code: {}", code);
        try {
            String originalUrl = urlShortenerService.getOriginalUrl(code);
            log.info("Original URL found {}", originalUrl);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", originalUrl)
                    .build();
        } catch (IllegalArgumentException e) {
            log.error("Original URL with code {} does not exist.", code);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
