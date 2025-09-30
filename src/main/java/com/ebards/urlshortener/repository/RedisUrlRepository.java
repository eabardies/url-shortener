package com.ebards.urlshortener.repository;

import com.ebards.urlshortener.model.UrlMapping;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class RedisUrlRepository implements UrlRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisUrlRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(UrlMapping urlMapping) {
        redisTemplate.opsForValue().set(urlMapping.getCode(), urlMapping.getOriginalUrl());
    }

    @Override
    public UrlMapping findByCode(String code) {
        String url = redisTemplate.opsForValue().get(code);
        return url != null ? new UrlMapping(code, url) : null;
    }
}
