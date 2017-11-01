package com.oath.micro.server.elasticache;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import net.spy.memcached.MemcachedClient;


@Slf4j
public class TransientElasticacheDataConnection<V> implements DistributedCacheManager<V> {

        private volatile boolean available = false;
        private final MemcachedClient memcachedClient;
        private final int retryAfterSec;
        private final int maxTry;

        public TransientElasticacheDataConnection(MemcachedClient memcachedClient,int retryAfterSec, int maxTry) {
            this.memcachedClient = memcachedClient;
            this.retryAfterSec = retryAfterSec;
            this.maxTry = maxTry;
        }

        @Override
        public boolean add(final String key, int exp, final Object value) {

            log.trace("Memcached add operation on key '{}', with value:{}", key, value);
            boolean success = false;
            int tryCount = 0;

            do {
                try {
                    if (tryCount > 0) {
                        Thread.sleep(retryAfterSec * 1000);
                        log.warn("retrying operation  #{}", tryCount);
                    }
                    tryCount++;
                    success = memcachedClient.add(key, exp, value)
                            .get();
                } catch (final Exception e) {
                    log.warn("memcache set: {}", e.getMessage());
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