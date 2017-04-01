package ru.mail.server.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ServerMemoryCache {

	private static final ServerMemoryCache instance = createCache(10000, 10, 1000000);
	private Cache<String, Object> cache;

    public Object get(String key) {
        return cache.getIfPresent(key);
    }

    public void put(String key, Object obj) {
        cache.put(key, obj);
    }

    public static ServerMemoryCache getInstance() {
    	return instance;
    }

	private static synchronized ServerMemoryCache createCache(int concurrencyLevel, int expiration, int size) {
		return new ServerMemoryCache(concurrencyLevel, expiration, size);
	}

	private ServerMemoryCache(int concurrencyLevel, int expiration, int size) {
		cache = CacheBuilder.newBuilder().concurrencyLevel(concurrencyLevel).maximumSize(size).softValues()
	            .expireAfterWrite(expiration, TimeUnit.MINUTES).build();
	}

}
