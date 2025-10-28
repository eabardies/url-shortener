package com.ebards.urlshortener.service;

import com.ebards.urlshortener.dtos.ShortenUrlRequest;
import com.ebards.urlshortener.exceptions.UrlNotFoundException;
import com.ebards.urlshortener.model.UrlMapping;
import com.ebards.urlshortener.repository.InMemoryUrlRepository;
import com.ebards.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InMemoryUrlShortenerServiceTest {
    private UrlRepository urlRepository;
    private UrlShortenerService inMemoryUrlShortenerService;

    @BeforeEach
    void setUp(){
        urlRepository = mock(InMemoryUrlRepository.class);
        inMemoryUrlShortenerService = new InMemoryUrlShortenerService("https://short.ly/", urlRepository);
    }

    @Test
    void shortenUrl_validUrl_returnsShortUrl() {
        String originalUrl = "https://example.com";
        var shortenUrlRequest = new ShortenUrlRequest();
        shortenUrlRequest.setUrl(originalUrl);

        var shortUrl = inMemoryUrlShortenerService.shortenUrl(shortenUrlRequest);

        assertNotNull(shortUrl);
        assertTrue(shortUrl.getShortenedUrl().startsWith("https://short.ly/"));
        verify(urlRepository, times(1)).save(any(UrlMapping.class));
    }

    @Test
    void getOriginalUrl_existingCode_returnsOriginalUrl() {
        UrlMapping mapping = new UrlMapping("abc123", "https://example.com", "abcd");
        when(urlRepository.findByCode("abc123")).thenReturn(mapping);

        String originalUrl = inMemoryUrlShortenerService.getOriginalUrl("abc123");

        assertEquals("https://example.com", originalUrl);
    }

    @Test
    void getOriginalUrl_nonExistingCode_throwsException() {
        when(urlRepository.findByCode("missing")).thenReturn(null);

        assertThrows(UrlNotFoundException.class,
                () -> inMemoryUrlShortenerService.getOriginalUrl("missing"));
    }
}
