package com.aol.micros.server.elasticache;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.junit.Assert.assertEquals;
import com.aol.micro.server.elasticache.TransientElasticacheDataConnection;
import net.spy.memcached.internal.OperationFuture;
import org.junit.Before;
import org.junit.Test;
import net.spy.memcached.MemcachedClient;
import java.util.Optional;

public class TransientElasticacheDataConnectionTest {

    MemcachedClient memcachedClient;

    @Before
    public void setup() {
        memcachedClient = mock(MemcachedClient.class);

        stub(memcachedClient.get("key1")).toReturn("value1");
        stub(memcachedClient.get("key2")).toReturn("value2");
        OperationFuture<Boolean> mockedFuture = mock(OperationFuture.class);
        stub(memcachedClient.add("keyAdd", 3600, "valueadd")).toReturn(mockedFuture);
    }

    @Test
    public void happyPathGetTest() {
        TransientElasticacheDataConnection transientClient = new TransientElasticacheDataConnection(memcachedClient, 3, 1);
        assertEquals(Optional.ofNullable("value1"), transientClient.get("key1"));
        assertEquals(Optional.ofNullable("value2"), transientClient.get("key2"));
    }

    @Test
    public void notExistingKeyGetTest() {
        TransientElasticacheDataConnection transientClient = new TransientElasticacheDataConnection(memcachedClient, 3, 1);
        assertEquals(Optional.empty(), transientClient.get("key3"));
    }

    @Test
    public void notExistingKeyPutTest() {
        TransientElasticacheDataConnection transientClient = new TransientElasticacheDataConnection(memcachedClient, 3, 1);
        assertEquals(false, transientClient.put("keyAdd", "valueadd"));
    }



}