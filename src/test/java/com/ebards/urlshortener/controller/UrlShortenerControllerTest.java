package com.ebards.urlshortener.controller;

import com.ebards.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlShortenerController.class)
class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlShortenerService urlShortenerService;

    @Test
    void testShortenUrl_success() throws Exception {
        String originalUrl = "https://example.com";
        String shortUrl = "https://short.ly/abc123";

        Mockito.when(urlShortenerService.shortenUrl(anyString())).thenReturn(shortUrl);

        mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + originalUrl + "\""))
                .andExpect(status().isOk())
                .andExpect(content().string(shortUrl));
    }

    @Test
    void testShortenUrl_invalidUrl() throws Exception {
        String originalUrl = "invalid-url";
        Mockito.when(urlShortenerService.shortenUrl(anyString()))
                .thenThrow(new IllegalArgumentException("Invalid URL"));

        mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + originalUrl + "\""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid URL"));
    }

    @Test
    void testRedirect_success() throws Exception {
        String code = "abc123";
        String originalUrl = "https://example.com";

        Mockito.when(urlShortenerService.getOriginalUrl(code)).thenReturn(originalUrl);

        mockMvc.perform(get("/api/{code}", code))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", originalUrl));
    }

    @Test
    void testRedirect_notFound() throws Exception {
        String code = "xyz999";

        Mockito.when(urlShortenerService.getOriginalUrl(code))
                .thenThrow(new IllegalArgumentException("Code not found"));

        mockMvc.perform(get("/api/{code}", code))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Code not found"));
    }

    @Test
    void testGetInfo_success() throws Exception {
        String code = "abc123";
        String originalUrl = "https://example.com";

        Mockito.when(urlShortenerService.getOriginalUrl(code)).thenReturn(originalUrl);

        mockMvc.perform(get("/api/info/{code}", code))
                .andExpect(status().isOk())
                .andExpect(content().string(originalUrl));
    }

    @Test
    void testGetInfo_invalidCode() throws Exception {
        String code = "abc123";

        Mockito.when(urlShortenerService.getOriginalUrl(code))
                .thenThrow(new IllegalArgumentException("Code not found"));

        mockMvc.perform(get("/api/info/{code}", code))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Code not found"));
    }
}
