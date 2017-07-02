package com.aol.micro.server.elasticache;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import net.spy.memcached.MemcachedClient;
import lombok.extern.slf4j.Slf4j;
import com.aol.micro.server.distributed.DistributedMap;


@Slf4j
public class TransientElasticacheDataConnection<V> implements DistributedMap<V> {

        private final MemcachedClient memcachedClient;
        private final int retryAfterSec;
        private final int maxTry;
        private final int defaultExpiry = 3600;

        public TransientElasticacheDataConnection(MemcachedClient memcachedClient,int retryAfterSec, int maxTry) {
            this.memcachedClient = memcachedClient;
            this.retryAfterSec = retryAfterSec;
            this.maxTry = maxTry;
        }

        @Override
        public boolean put(final String key, final Object value) {
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
                    success = memcachedClient.add(key, defaultExpiry, value)
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
            return success;
        }

    @Override
    public boolean put(final String key, int expiry, final Object value) {
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
                success = memcachedClient.add(key, expiry, value)
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
        return success;
    }

        @Override
        public Optional<V> get(String key) {
            return (Optional<V>) Optional.ofNullable(memcachedClient.get(key));
        }

    @Override
    public void delete(String key) {

    }

}