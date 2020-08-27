package com.oath.micro.server.memcached;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;

@Component
@Slf4j
public class DistributedCacheFactory {

    private final ElasticacheConfig config;

    @Autowired
    public DistributedCacheFactory(ElasticacheConfig config) {
        this.config = config;
    }

    public <K, V> DistributedCache<K, V> create() {
        try {
            log.info("Creating Memcached Data connection for elasticache cluster: {}", config.getHostname());
            return new MemcachedCacheImpl(createMemcachedClient(), config.getRetryAfterSecs(), config.getMaxRetries());
        }
        catch (Exception e) {
            log.error("Failed to create transient data connection", e);
            return null;
        }
    }

    public MemcachedClient createMemcachedClient() {
        try {
            log.info("Starting an instance of memcache client towards elasticache cluster");
            return new MemcachedClient(new InetSocketAddress(config.getHostname(), config.getPort()));
        } catch (IOException e) {
            log.error("Could not initialize connection to elasticache cluster", e);
            return null;
        }

    }
}
