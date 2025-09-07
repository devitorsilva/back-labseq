package org.acme.Services;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class CacheService {
    private final ConcurrentHashMap<Long, BigInteger> cache = new ConcurrentHashMap<>();

    public void put(Long key, BigInteger value) {
        cache.put(key, value);
    }

    public BigInteger get(Long key) {
        return cache.get(key);
    }

    public boolean containsKey(Long key) {
        return cache.containsKey(key);
    }

    public Map<Long, BigInteger> getAll() {
        return cache;
    }

    public Integer size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
    }
}
