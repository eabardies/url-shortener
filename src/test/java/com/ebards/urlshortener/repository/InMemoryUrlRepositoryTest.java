package com.ebards.urlshortener.repository;

import com.ebards.urlshortener.model.UrlMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryUrlRepositoryTest {
    private InMemoryUrlRepository inMemoryUrlRepository;

    @BeforeEach
    public void setup() {
        inMemoryUrlRepository = new InMemoryUrlRepository();
    }

    @Test
    void testSaveAndFindByCode_success() {
        UrlMapping mapping = new UrlMapping("abc123", "https://example.com", "abcd");

        inMemoryUrlRepository.save(mapping);
        UrlMapping result = inMemoryUrlRepository.findByCode("abc123");

        assertNotNull(result);
        assertEquals("abc123", result.getCode());
        assertEquals("https://example.com", result.getOriginalUrl());
        assertEquals("abcd", result.getShortenedUrl());
    }

    @Test
    void testFindByCode_notFound() {
        UrlMapping result = inMemoryUrlRepository.findByCode("doesNotExist");

        assertNull(result, "Expected null when no mapping exists for given code");
    }

    @Test
    void testOverwriteMapping_sameCode() {
        UrlMapping mapping1 = new UrlMapping("abc123", "https://example.com", "abcd");
        UrlMapping mapping2 = new UrlMapping("abc123", "https://another.com", "abcd");

        inMemoryUrlRepository.save(mapping1);
        inMemoryUrlRepository.save(mapping2);

        UrlMapping result = inMemoryUrlRepository.findByCode("abc123");

        assertNotNull(result);
        assertEquals("https://another.com", result.getOriginalUrl(), "Latest mapping should overwrite the old one");
    }
}
