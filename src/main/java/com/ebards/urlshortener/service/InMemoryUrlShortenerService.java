package com.ebards.urlshortener.service;

import com.ebards.urlshortener.dtos.ShortenUrlRequest;
import com.ebards.urlshortener.exceptions.UrlNotFoundException;
import com.ebards.urlshortener.model.UrlMapping;
import com.ebards.urlshortener.repository.UrlRepository;
import com.ebards.urlshortener.utils.Base62GeneratorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class InMemoryUrlShortenerService implements UrlShortenerService {

    private final String baseUrl;
    private final UrlRepository urlRepository;

    public InMemoryUrlShortenerService(
            @Value("${app.base-url}") String baseUrl,
            UrlRepository urlRepository) {
        this.baseUrl = baseUrl;
        this.urlRepository = urlRepository;
    }

    @Override
    public UrlMapping shortenUrl(ShortenUrlRequest request) {
        var code = Base62GeneratorUtil.generateCode();
        var shortenedUrl = baseUrl + code;
        var urlMapping = new UrlMapping(code, request.getUrl(), shortenedUrl);

        urlRepository.save(urlMapping);
        return urlMapping;
    }

    @Override
    public String getOriginalUrl(String code) {
        UrlMapping urlMapping = urlRepository.findByCode(code);

        if (urlMapping == null) {
            throw new UrlNotFoundException();
        }

        return urlMapping.getOriginalUrl();
    }

}
