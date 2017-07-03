package com.aol.micro.server.couchbase;

/**
 * Created by gordonmorrow on 03/07/2017.
 */

import com.aol.micro.server.distributed.DistributedMap;
import com.aol.micro.server.events.ScheduledJob;
import com.aol.micro.server.events.SystemData;
import com.couchbase.client.CouchbaseClient;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
    public class CouchbaseConnectionTester implements ScheduledJob {

        private static final Random random = new Random();

        private final DistributedMap cache;
        private final CouchbaseClient couchbaseClient;

        public CouchbaseConnectionTester(DistributedMap cache, CouchbaseClient couchbaseClient) {

            this.cache = cache;
            this.couchbaseClient = couchbaseClient;
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
            couchbaseClient.set(key, 120, testValue);
            int received = (Integer) couchbaseClient.get(key);

            return received == testValue;
        }

}