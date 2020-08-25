package com.oath.micro.server.elasticache;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
public class ElasticacheConnection<K, V> implements DistributedCache<K, V> {

        private volatile boolean available = false;
        private final MemcachedClient memcachedClient;
        private final int retryAfterSec;
        private final int maxTry;

        public ElasticacheConnection(MemcachedClient memcachedClient, int retryAfterSec, int maxTry) {
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
        public boolean isAvailable() {
            return available;
        }

        @Override
        public final void setConnectionTested(final boolean available) {
            this.available = available;
        }

        private String asString(K key){
            return key.toString();
        }
}
