package com.oath.micro.server.memcached;



import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Slf4j
@Getter
@Configuration
public class ElasticacheConfig {
    private final String hostname;
    private final int port;
    private final int retryAfterSecs;
    private final int maxRetries;

    @Autowired
    public ElasticacheConfig(@Value("${elasticache.hostname:null}") String hostname,
                             @Value("${elasticache.port:6379}") int port,
                             @Value("${elasticache.retry.after.seconds:1}") int retryAfterSecs,
                             @Value("${elasticache.max.retries:3}") int maxRetries) {
        this.hostname = hostname;
        this.port = port;
        this.retryAfterSecs = retryAfterSecs;
        this.maxRetries = maxRetries;
    }
}

