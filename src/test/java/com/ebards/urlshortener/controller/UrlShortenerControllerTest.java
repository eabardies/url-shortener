package com.ebards.urlshortener.controller;

import com.ebards.urlshortener.dtos.OriginalUrlResponse;
import com.ebards.urlshortener.dtos.ShortenUrlRequest;
import com.ebards.urlshortener.exceptions.UrlNotFoundException;
import com.ebards.urlshortener.model.UrlMapping;
import com.ebards.urlshortener.service.UrlShortenerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlShortenerController.class)
class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UrlShortenerService urlShortenerService;

    @Test
    void shortenUrl_validRequest_returnsOkAndBody() throws Exception {
        ShortenUrlRequest request = new ShortenUrlRequest();
        request.setUrl("https://example.com");

        UrlMapping returned = new UrlMapping("abc123", request.getUrl(), "https://short.ly/abc123");

        when(urlShortenerService.shortenUrl(any(ShortenUrlRequest.class))).thenReturn(returned);

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("abc123"))
                .andExpect(jsonPath("$.originalUrl").value("https://example.com"))
                .andExpect(jsonPath("$.shortenedUrl").value("https://short.ly/abc123"));

        verify(urlShortenerService).shortenUrl(any(ShortenUrlRequest.class));
    }

    @Test
    void shortenUrl_invalidRequest_returnsBadRequest() throws Exception {
        ShortenUrlRequest invalid = new ShortenUrlRequest();
        invalid.setUrl("not-a-valid-url");

        mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(urlShortenerService, org.mockito.Mockito.never()).shortenUrl(any());
    }

    @Test
    void redirect_existingCode_returns302WithLocationHeader() throws Exception {
        String code = "abc123";
        String originalUrl = "https://example.com/page";

        when(urlShortenerService.getOriginalUrl(eq(code))).thenReturn(originalUrl);

        mockMvc.perform(get("/api/" + code))
                .andExpect(status().isFound()) // 302
                .andExpect(header().string("Location", originalUrl));

        verify(urlShortenerService).getOriginalUrl(eq(code));
    }

    @Test
    void redirect_nonExistingCode_serviceThrowsNotFound_returns404() throws Exception {
        String code = "doesnotexist";

        when(urlShortenerService.getOriginalUrl(eq(code)))
                .thenThrow(new UrlNotFoundException());

        mockMvc.perform(get("/api/" + code))
                .andExpect(status().isBadRequest());

        verify(urlShortenerService).getOriginalUrl(eq(code));
    }

    @Test
    void getInfo_existingCode_returns200() throws Exception {
        String code = "abc123";
        String originalUrl = "https://example.com/page";

        when(urlShortenerService.getOriginalUrl(eq(code))).thenReturn(originalUrl);

        mockMvc.perform(get("/api/info/" + code))
                .andExpect(status().isOk());

        verify(urlShortenerService).getOriginalUrl(eq(code));
    }

    @Test
    void getInfo_invalidCode_returns400() throws Exception {
        String code = "doesnotexist";

        when(urlShortenerService.getOriginalUrl(eq(code)))
                .thenThrow(new UrlNotFoundException());

        mockMvc.perform(get("/api/info/" + code))
                .andExpect(status().isBadRequest());

        verify(urlShortenerService).getOriginalUrl(eq(code));
    }


}
