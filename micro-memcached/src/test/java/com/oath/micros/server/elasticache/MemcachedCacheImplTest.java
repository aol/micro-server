package com.oath.micros.server.elasticache;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.oath.micro.server.memcached.MemcachedCacheImpl;
import net.spy.memcached.internal.OperationFuture;
import org.junit.Before;
import org.junit.Test;
import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class MemcachedCacheImplTest {

    MemcachedClient memcachedClient;
    OperationFuture<Boolean> mockedFuture;

    @Before
    public void setup() {
        memcachedClient = mock(MemcachedClient.class);

        stub(memcachedClient.get("key1")).toReturn("value1");
        stub(memcachedClient.get("key2")).toReturn("value2");
        stub(memcachedClient.getStats()).toReturn(getStatsMap());
        mockedFuture = mock(OperationFuture.class);
        stub(memcachedClient.add("keyAdd", 3600, "valueadd")).toReturn(mockedFuture);
        stub(memcachedClient.flush()).toReturn(mockedFuture);
    }

    Map<SocketAddress, Map<String, String>> getStatsMap(){
        Map<String, String> statsMap = new HashMap<>();
        Map<SocketAddress, Map<String, String>> statsMapForAllHost = new HashMap<>();
        statsMap.put("hitCount", "10");
        statsMap.put("missCount", "1");
        statsMapForAllHost.put(new InetSocketAddress(1234), statsMap);
        return statsMapForAllHost;
    }

    @Test
    public void happyPathGetTest() {
        MemcachedCacheImpl cache = new MemcachedCacheImpl(memcachedClient, 3, 1);
        assertEquals(Optional.ofNullable("value1"), cache.get("key1"));
        assertEquals(Optional.ofNullable("value2"), cache.get("key2"));
    }

    @Test
    public void notExistingKeyGetTest() {
        MemcachedCacheImpl cache = new MemcachedCacheImpl(memcachedClient, 3, 1);
        assertEquals(Optional.empty(), cache.get("key3"));
    }

    @Test
    public void notExistingKeyPutTest() throws ExecutionException, InterruptedException {
        MemcachedCacheImpl cache = new MemcachedCacheImpl(memcachedClient, 3, 1);
        when(mockedFuture.get()).thenReturn(false);
        assertFalse(cache.add("keyAdd", 3600, "valueadd"));
    }

    @Test
    public void existingKeyPutTest() throws ExecutionException, InterruptedException {
        MemcachedCacheImpl cache = new MemcachedCacheImpl(memcachedClient, 3, 1);
        when(mockedFuture.get()).thenReturn(true);
        assertTrue(cache.add("keyAdd", 3600, "valueadd"));
    }

    @Test
    public void existingKeyPutTestWithRetry() throws ExecutionException, InterruptedException {
        MemcachedCacheImpl cache = new MemcachedCacheImpl(memcachedClient, 3, 2);
        when(mockedFuture.get()).thenReturn(false, true);
        assertTrue(cache.add("keyAdd", 3600, "valueadd"));
    }

    @Test
    public void testIsAvailableFalse() {
        MemcachedCacheImpl cache = new MemcachedCacheImpl(memcachedClient, 3, 1);
        cache.setConnectionTested(false);
        assertFalse(cache.isAvailable());
    }

    @Test
    public void testIsAvailableTrue() {
        MemcachedCacheImpl cache = new MemcachedCacheImpl(memcachedClient, 3, 1);
        cache.setConnectionTested(true);
        assertTrue(cache.isAvailable());
    }

    @Test
    public void testFlush() throws ExecutionException, InterruptedException {
        MemcachedCacheImpl cache = new MemcachedCacheImpl(memcachedClient, 3, 1);
        when(mockedFuture.get()).thenReturn(false);
        assertFalse((boolean)cache.flush().get());
    }

    @Test
    public void testGetStats() {
        MemcachedCacheImpl cache = new MemcachedCacheImpl(memcachedClient, 3, 1);
        assertEquals(cache.getStats().toString(), "{0.0.0.0/0.0.0.0:1234={hitCount=10, missCount=1}}");
    }
}
