package com.ebards.urlshortener.controller;

import com.ebards.urlshortener.dtos.ErrorDto;
import com.ebards.urlshortener.dtos.OriginalUrlResponse;
import com.ebards.urlshortener.dtos.ShortenUrlRequest;
import com.ebards.urlshortener.exceptions.UrlNotFoundException;
import com.ebards.urlshortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @PostMapping()
    public ResponseEntity<?> shortenUrl(@Valid @RequestBody ShortenUrlRequest request) {
        var urlMapping = urlShortenerService.shortenUrl(request);
        return ResponseEntity.ok(urlMapping);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable(name = "code") String code) {
        String originalUrl = urlShortenerService.getOriginalUrl(code);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", originalUrl)
                .build();
    }

    @GetMapping("info/{code}")
    public ResponseEntity<OriginalUrlResponse> getInfo(@PathVariable(name = "code") String code) {
        var originalUrl = urlShortenerService.getOriginalUrl(code);
        var test = urlShortenerService.getOriginalUrl(code);
        return ResponseEntity.ok(new OriginalUrlResponse(originalUrl));
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<?> handleUrlNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(e.getMessage()));
    }
}
