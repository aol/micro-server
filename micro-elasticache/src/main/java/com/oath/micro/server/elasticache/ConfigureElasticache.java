package com.oath.micro.server.elasticache;



import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;


import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Slf4j
@Configuration
public class ConfigureElasticache {


    private final String hostname;
    private final int port;
    private final int retryAfterSecs;
    private final int maxRetries;

    @Autowired
    public ConfigureElasticache( @Value("${elasticache.hostname:null}") String hostname,
                                 @Value("${elasticache.port:6379}") int port,
                                 @Value("${elasticache.retry.after.seconds:1}") int retryAfterSecs,
                                 @Value("${elasticache.max.retries:3}") int maxRetries) {
        this.hostname = hostname;
        this.port = port;
        this.retryAfterSecs = retryAfterSecs;
        this.maxRetries = maxRetries;
    }


    @Bean(name = "transientCache")
    public DistributedCacheManager transientCache() throws IOException, URISyntaxException {
        try {
            log.info("Creating Memcached Data connection for elasticache cluster: {}", hostname);
            return new TransientElasticacheDataConnection(createMemcachedClient(), retryAfterSecs, maxRetries);
        }
     catch (Exception e) {
            log.error("Failed to create transient data connection", e);
            return null;
        }
    }

    @Bean(name = "memcachedClient")
    public MemcachedClient createMemcachedClient() throws IOException {
        try {
            log.info("Starting an instance of memcache client towards elasticache cluster");
            return new MemcachedClient(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            log.error("Could not initilise connection to elasticache cluster", e);
            return null;
        }

    }
}

