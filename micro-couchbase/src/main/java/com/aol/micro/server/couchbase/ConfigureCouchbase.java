package com.aol.micro.server.couchbase;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.aol.micro.server.couchbase.manifest.comparator.CouchbaseManifestComparator;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;

import lombok.Setter;

@Slf4j
@Configuration
public class ConfigureCouchbase {

    @Value("${couchbase.manifest.comparison.key:default-key}")
    private String defaultCouchbaseManifestComparisonKey;
    @Setter
    @Value("${couchbaseServers:}")
    private String couchbaseServers;

    @Value("${couchbaseBucket:couchbase_bucket}")
    private String couchbaseBucket;

    @Value("${couchbasePassword:}")
    private String couchbasePassword;

    @Setter
    @Value("${couchbaseClientEnabled:true}")
    private boolean couchbaseClientEnabled = true;

    @Value("${couchbaseClientOperationTimeout:120000}")
    private long opTimeout;

    @Value("${distributed.cache.default.expiration:691200}")
    private int expiresAfterSeconds = 691200;

    @Value("${distributed.cache.maxTry:5}")
    private int maxTry = 5;

    @Value("${distributed.cache.retryAfterSec:1}")
    private int retryAfterSec = 1;

    @SuppressWarnings("rawtypes")
    @Bean(name = "couchbaseDistributedMap")
    public CouchbaseDistributedCacheClient simpleCouchbaseClient() throws IOException, URISyntaxException {
        if (couchbaseClientEnabled) {
            return new CouchbaseDistributedCacheClient(
                                                     couchbaseClient(), expiresAfterSeconds, maxTry,
                    retryAfterSec);
        } else {
            return new CouchbaseDistributedCacheClient(
                                                     null, expiresAfterSeconds, maxTry,
                    retryAfterSec);
        }
    }

    @Bean(name = "couchbaseClient")
    public CouchbaseClient couchbaseClient() throws IOException, URISyntaxException {
        if (couchbaseClientEnabled) {
            log.info("Creating CouchbaseClient for servers: {}", couchbaseServers);
            CouchbaseConnectionFactoryBuilder builder = new CouchbaseConnectionFactoryBuilder();
            builder.setOpTimeout(opTimeout);
            CouchbaseConnectionFactory cf = builder.buildCouchbaseConnection(getServersList(), couchbaseBucket,
                                                                             StringUtils.trimAllWhitespace(Optional.ofNullable(couchbasePassword)
                                                                                                                   .orElse("")));
            return new CouchbaseClient(
                                       cf);
        }
        return null;
    }

    @Bean
    public CouchbaseManifestComparator couchbaseManifestComparator() throws IOException, URISyntaxException {
        return new CouchbaseManifestComparator(
                                               this.simpleCouchbaseClient()).withKey(defaultCouchbaseManifestComparisonKey);
    }

    private List<URI> getServersList() throws URISyntaxException {
        List<URI> uris = new ArrayList<URI>();
        if (couchbaseServers.indexOf(',') == -1) {
            uris.add(new URI(
                             couchbaseServers));
            return uris;
        }

        for (String serverHost : StringUtils.split(couchbaseServers, ",")) {
            uris.add(new URI(
                             serverHost));
        }
        return uris;
    }

}
