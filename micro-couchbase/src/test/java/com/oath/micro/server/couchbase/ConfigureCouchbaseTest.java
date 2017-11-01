package com.oath.micro.server.couchbase;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.distributed.DistributedMap;

public class ConfigureCouchbaseTest {

    ConfigureCouchbase config;

    @Before
    public void setUp() throws Exception {
        config = new ConfigureCouchbase();
    }

    @Test
    public void createDistributedCacheMemcachedOff() throws IOException, URISyntaxException {
        config.setCouchbaseClientEnabled(false);
        DistributedMap<Object> cache = config.simpleCouchbaseClient();
        assertThat(cache.get("hello"), is(Optional.empty()));

    }

    @Test(expected = NullPointerException.class)
    public void createDistributedCache() throws IOException, URISyntaxException {
        memcachedOn();
        config.simpleCouchbaseClient();
        fail("Memcache should throw exception");

    }

    private void memcachedOn() {
        config.setCouchbaseClientEnabled(true);
        config.setCouchbaseServers(null);
    }

}