package com.aol.micro.server.elasticache;

/**
 * Created by gordonmorrow on 4/24/17.
 */

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;


import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;

import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
public class ConfigureElasticache {

    private ClientMode mode = ClientMode.Dynamic;

    @Value("${elasticache.hostname:lana-staging-ec.6o3auf.cfg.use1.cache.amazonaws.com")
    private String hostname = "lana-staging-ec.6o3auf.cfg.use1.cache.amazonaws.com";

    @Value("${elasticache.port:6379")
    private int port = 6379;

    @Value("${elasticache.retries:3")
    private int retries = 3;

    @Value("${elastiache.retry.after.seconds:1")
    private int retryAfterSeconds = 1;

    private MemcachedClient cache;

    InetSocketAddress socketAddress = new InetSocketAddress(hostname, port);

    @Bean(name = "elasticacheClient")
    public TransientElasticacheDataConnection transientCache() throws IOException, URISyntaxException {
        log.info("Creating MemcacheClient for servers: {}", hostname);
        return new TransientElasticacheDataConnection(createMemcachedClient(socketAddress), retries, retryAfterSeconds);
    }

    private MemcachedClient createMemcachedClient(InetSocketAddress socketAddress) throws IOException {

        try {
            return new MemcachedClient(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}

