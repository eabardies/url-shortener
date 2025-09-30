package com.ebards.urlshortener.service;

import com.ebards.urlshortener.model.UrlMapping;
import com.ebards.urlshortener.repository.RedisUrlRepository;
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
        urlRepository = mock(RedisUrlRepository.class);
        inMemoryUrlShortenerService = new InMemoryUrlShortenerService(urlRepository);
    }

    @Test
    void shortenUrl_validUrl_returnsShortUrl() {
        String originalUrl = "https://example.com";
        String shortUrl = inMemoryUrlShortenerService.shortenUrl(originalUrl);

        assertNotNull(shortUrl);
        assertTrue(shortUrl.startsWith("https://short.ly/"));
        verify(urlRepository, times(1)).save(any(UrlMapping.class));
    }

    @Test
    void shortenUrl_invalidUrl_throwsException() {
        String invalidUrl = "invalid-url";

        assertThrows(IllegalArgumentException.class,
                () -> inMemoryUrlShortenerService.shortenUrl(invalidUrl));
    }

    @Test
    void getOriginalUrl_existingCode_returnsOriginalUrl() {
        UrlMapping mapping = new UrlMapping("abc123", "https://example.com");
        when(urlRepository.findByCode("abc123")).thenReturn(mapping);

        String originalUrl = inMemoryUrlShortenerService.getOriginalUrl("abc123");

        assertEquals("https://example.com", originalUrl);
    }

    @Test
    void getOriginalUrl_nonExistingCode_throwsException() {
        when(urlRepository.findByCode("missing")).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> inMemoryUrlShortenerService.getOriginalUrl("missing"));
    }
}
