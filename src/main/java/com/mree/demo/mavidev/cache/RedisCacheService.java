package com.mree.demo.mavidev.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisCacheService implements CacheService {

    private final RedisTemplate<String, Object> objectRedisTemplate;

    public RedisCacheService(RedisTemplate<String, Object> objectRedisTemplate) {
        this.objectRedisTemplate = objectRedisTemplate;
    }

    @Override
    public void set(String key, Object value) {
        objectRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, Duration ttl) {
        objectRedisTemplate.opsForValue().set(key, value, ttl.getSeconds(), TimeUnit.SECONDS);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> get(String key) {

        Object value = objectRedisTemplate.opsForValue().get(key);
        if (value == null)
            return Optional.empty();

        return Optional.of((T) value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> getWithPattern(String pattern) {
        Set<String> keys = objectRedisTemplate.keys(pattern);
        Map<String, T> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(keys)) {
            for (String key : keys) {
                Object value = objectRedisTemplate.opsForValue().get(key);
                if (value != null) {
                    map.put(key, (T) value);
                }
            }
        }
        return map;
    }

    @Override
    public void delete(String key) {
        objectRedisTemplate.delete(key);
    }

    @Override
    public void deleteWithPattern(String keyPattern) {
        Set<String> keys = objectRedisTemplate.keys(keyPattern);
        Long numOfDeleted = 0L;

        if (!CollectionUtils.isEmpty(keys))
            numOfDeleted = objectRedisTemplate.delete(keys);

        log.debug("Number of deleted keys: {}", numOfDeleted);
    }

    @Override
    public Boolean hasKey(String key) {
        return objectRedisTemplate.hasKey(key);
    }

    @Override
    public Long getRemainingTtlAsSeconds(String key) {
        return objectRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}
