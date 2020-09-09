package com.oath.micro.server.memcached;

import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;


@Slf4j
public class MemcachedCacheImpl<K, V> implements DistributedCache<K, V> {

    private final MemcachedClient memcachedClient;
    private final int retryAfterSec;
    private final int maxTry;
    private volatile boolean available = false;

    public MemcachedCacheImpl(MemcachedClient memcachedClient, int retryAfterSec, int maxTry) {
        this.memcachedClient = memcachedClient;
        this.retryAfterSec = retryAfterSec;
        this.maxTry = maxTry;
    }

    @Override
    public boolean add(final K key, int exp, final V value) {

        log.trace("Memcached add operation on key '{}', with value:{}", key, value);
        boolean success = false;
        int tryCount = 0;

        do {
            try {
                if (tryCount > 0) {
                    Thread.sleep(retryAfterSec * 1000);
                    log.warn("Retrying operation  #{}", tryCount);
                }
                tryCount++;
                success = memcachedClient.add(asString(key), exp, value)
                    .get();
            } catch (final Exception e) {
                log.warn("Memcache set: {}", e.getMessage());
            }
        } while (!success && tryCount < maxTry);

        if (!success) {
            log.error("Failed to add key to Elasticache {}", key);
        }
        if (success && tryCount > 1) {
            log.info("Connection restored OK to Elasticache cluster");
        }

        available = success;
        return success;
    }

    @Override
    public Optional<V> get(K key) {
        return (Optional<V>) Optional.ofNullable(memcachedClient.get(asString(key)));
    }

    @Override
    public Future<Boolean> flush() {
        return memcachedClient.flush();
    }

    @Override
    public Map<SocketAddress, Map<String, String>> getStats() {
        return memcachedClient.getStats();
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public final void setConnectionTested(final boolean available) {
        this.available = available;
    }

    private String asString(K key) {
        return key.toString().replaceAll("\\s+","");
    }
}
