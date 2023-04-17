package com.mree.demo.mavidev.cache;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

public interface CacheService {

    void set(String key, Object value);

    void set(String key, Object value, Duration ttl);

    <T> Optional<T> get(String key);

    <T> Map<String, T> getWithPattern(String pattern);

    void delete(String key);

    void deleteWithPattern(String keyPattern);

    Boolean hasKey(String key);

    Long getRemainingTtlAsSeconds(String key);
}
