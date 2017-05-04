package com.aol.micro.server.elasticache;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import net.spy.memcached.MemcachedClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by gordonmorrow on 5/3/17.
 */
@Component
@Slf4j
public class TransientElasticacheDataConnection<V> implements DistributedCacheManager<V> {

        private volatile boolean available = false;
        private final MemcachedClient memcachedClient;
        private final int maxTry, retryAfterSec;

        public TransientElasticacheDataConnection(MemcachedClient memcachedClient, final int maxTry, final int retryAfterSec) {
            this.memcachedClient = memcachedClient;
            this.maxTry = maxTry;
            this.retryAfterSec = retryAfterSec;
        }

        @Override
        public boolean put(final String key, int exp, final V value) {

            log.trace("put '{}', value:{}", key, value);
            boolean success = false;
            int tryCount = 0;

            do {
                try {
                    if (tryCount > 0) {
                        Thread.sleep(retryAfterSec * 1000);
                        log.warn("retry #{}", tryCount);
                    }
                    tryCount++;
                    success = memcachedClient.set(key, exp, value)
                            .get();
                } catch (final Exception e) {
                    log.warn("memcache put: {}", e.getMessage());
                }
            } while (!success && tryCount < maxTry);

            if (!success) {
                log.error("Failed to add key to Elasticache {}", key);
            }
            if (success && tryCount > 1) {
                log.info("Connection restored OK");
            }

            available = success;
            return success;
        }

        @Override
        public Optional<V> get(String key) {
            return (Optional<V>) Optional.ofNullable(memcachedClient.get(key));
        }

        @Override
        public boolean isAvailable() {
            return available;
        }

        @Override
        public final void setConnectionTested(final boolean available) {
            this.available = available;
        }

    }

