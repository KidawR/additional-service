package com.example.additionalservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.example.additionalservice.model.Artist;


import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCache {

    private final RedisTemplate<String, Artist> redisTemplate;

    @Value("${customer.cache.ttl-seconds:1800}")
    private long ttlSeconds;

    private static final String PREFIX = "artist:";

    public Optional<Artist> get(Long id) {
        try {
            ValueOperations<String, Artist> ops = redisTemplate.opsForValue();
            return Optional.ofNullable(ops.get(PREFIX + id));
        } catch (DataAccessException e) {
            log.warn("Redis GET failed for key '{}': {}", PREFIX + id, e.getMessage());
            return Optional.empty();
        }
    }

    public void put(Artist artist) {
        if (artist != null && artist.getId() != null) {
            try {
                redisTemplate.opsForValue().set(PREFIX + artist.getId(), artist, Duration.ofSeconds(ttlSeconds));
            } catch (DataAccessException e) {
                log.warn("Redis PUT failed for artist '{}': {}", artist.getId(), e.getMessage());
            }
        }
    }

    public void remove(Long id) {
        try {
            redisTemplate.delete(PREFIX + id);
        } catch (DataAccessException e) {
            log.warn("Redis DELETE failed for key '{}': {}", PREFIX + id, e.getMessage());
        }
    }

    public boolean contains(Long id) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + id));
        } catch (DataAccessException e) {
            log.warn("Redis EXISTS failed for key '{}': {}", PREFIX + id, e.getMessage());
            return false;
        }
    }

    public int size() {
        try {
            Set<String> keys = redisTemplate.keys(PREFIX + "*");
            return keys != null ? keys.size() : 0;
        } catch (DataAccessException e) {
            log.warn("Redis SIZE operation failed: {}", e.getMessage());
            return 0;
        }
    }
}
