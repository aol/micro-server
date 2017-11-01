package com.oath.micro.server.couchbase;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.oath.micro.server.couchbase.base.CouchbaseManifestComparator;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;

import lombok.Setter;

@Configuration
public class ConfigureCouchbase {

    private Logger logger = LoggerFactory.getLogger(getClass());

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

    @SuppressWarnings("rawtypes")
    @Bean(name = "couchbaseDistributedMap")
    public CouchbaseDistributedMapClient simpleCouchbaseClient() throws IOException, URISyntaxException {
        if (couchbaseClientEnabled) {
            return new CouchbaseDistributedMapClient(
                                                     couchbaseClient());
        } else {
            return new CouchbaseDistributedMapClient(
                                                     null);
        }
    }

    @Bean(name = "couchbaseClient")
    public CouchbaseClient couchbaseClient() throws IOException, URISyntaxException {
        if (couchbaseClientEnabled) {
            logger.info("Creating CouchbaseClient for servers: {}", couchbaseServers);
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
