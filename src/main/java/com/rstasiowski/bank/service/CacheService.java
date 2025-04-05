package com.rstasiowski.bank.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class CacheService {

    private final RedisTemplate<String,Object> redisTemplate;

    public void saveToCache(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public Object loadFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteFromCache(String key) {
        redisTemplate.delete(key);
    }

}
