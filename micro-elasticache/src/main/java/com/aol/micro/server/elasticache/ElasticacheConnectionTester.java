package com.aol.micro.server.elasticache;

import com.aol.micro.server.distributed.DistributedCache;
import com.aol.micro.server.events.ScheduledJob;
import com.aol.micro.server.events.SystemData;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;

import java.util.Random;

@Slf4j
    public class ElasticacheConnectionTester implements ScheduledJob {

        private static final Random random = new Random();

        private final DistributedCache cache;
        private final MemcachedClient memcachedClient;

        public ElasticacheConnectionTester(DistributedCache cache, MemcachedClient memcachedClient) {

            this.cache = cache;
            this.memcachedClient = memcachedClient;
        }

        @Override
        public SystemData scheduleAndLog() {

            log.trace("runTestConnection()...");
            boolean result = false;
            try {
                result = testConnection();
            } catch (RuntimeException e) {
                log.debug("Could not connect to Cache" + e.getMessage());
            }
                cache.setConnectionTested(result);

            log.debug("Testing Couchbase connection: {}", result);
            return null;

        }

        private boolean testConnection() {
            String key = "PING_TEST";
            log.trace("Testing connection using key {}", key);

            int testValue = random.nextInt(1111);
            memcachedClient.set(key, 120, testValue);
            int received = (Integer) memcachedClient.get(key);

            return received == testValue;
        }

}