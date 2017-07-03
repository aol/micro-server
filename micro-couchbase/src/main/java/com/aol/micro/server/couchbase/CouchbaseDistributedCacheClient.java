package com.aol.micro.server.couchbase;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.aol.cyclops2.util.ExceptionSoftener;
import com.aol.micro.server.distributed.DistributedCache;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.CouchbaseClient;

@Slf4j
public class CouchbaseDistributedCacheClient<V> implements DistributedCache<V> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private volatile boolean available = false;

    private final Optional<CouchbaseClient> couchbaseClient;
    private final int expiresAfterSeconds, maxTry, retryAfterSec;

    public CouchbaseDistributedCacheClient(CouchbaseClient couchbaseClient, final int expiresAfterSeconds,
                                           final int maxTry, final int retryAfterSec) {

        this.couchbaseClient = Optional.ofNullable(couchbaseClient);
        this.expiresAfterSeconds = expiresAfterSeconds;
        this.maxTry = maxTry;
        this.retryAfterSec = retryAfterSec;
    }

    @Override
    public boolean put(final String key, final V value) {

        log.trace("put '{}', value:{}", key, value);
        boolean success = false;
        int tryCount = 0;

        do {
            try {
                if (tryCount > 0) {
                    Thread.sleep(retryAfterSec * 1000);
                    log.warn("retry #{}", tryCount);
                }
                tryCount++;
                success = couchbaseClient.map(c -> putInternal(c, key, value))
                        .orElse(false);

            } catch (final Exception e) {

                log.warn("memcache put: {}", e.getMessage());
            }
        } while (!success && tryCount < maxTry);

        if (!success) {
            log.error("Failed to place item in couchbase");
        }
        if (success && tryCount > 1) {
            log.info("Connection restored OK");
        }

        setConnectionTested(success);

        return success;
    }

    @Override
    public boolean put(final String key, int expiry, final V value) {
        logger.debug("put '{}', value:{}", key, value);
        boolean success = false;
        int tryCount = 0;

        do {
            try {
                if (tryCount > 0) {
                    Thread.sleep(retryAfterSec * 1000);
                    log.warn("retry #{}", tryCount);
                }
                tryCount++;
                success = couchbaseClient.map(c -> putInternalWithExpiry(c, key, value, expiry))
                .orElse(false);
            } catch (final Exception e) {

                log.warn("memcache put: {}", e.getMessage());
            }
        } while (!success && tryCount < maxTry);

        if (!success) {
            log.error("Failed to place item in couchbase");
        }
        if (success && tryCount > 1) {
            log.info("Connection restored OK");
        }

        setConnectionTested(success);

        return success;

    }

    private boolean putInternalWithExpiry(final CouchbaseClient client, final String key, final V value, int expiry) {

        try {
            return client.set(key,expiry, value)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw ExceptionSoftener.throwSoftenedException(e);

        }
    }


    private boolean putInternal(final CouchbaseClient client, final String key, final V value) {

        try {
            return client.set(key, value)
                         .get();
        } catch (InterruptedException | ExecutionException e) {
            throw ExceptionSoftener.throwSoftenedException(e);

        }
    }

    @Override
    public Optional<V> get(String key) {
        return couchbaseClient.map(c -> (V) c.get(key));
    }

    @Override
    public void delete(String key) {
        couchbaseClient.map(c -> c.delete(key));
    }

    @Override
    public boolean isAvailable(){
        return available;
    }

    @Override
    public void setConnectionTested(boolean result){
        available = result;
    }

}
