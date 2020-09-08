package com.oath.micro.server.memcached;

import java.net.SocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

public interface DistributedCache<K, V> {
        void setConnectionTested(boolean result);
        boolean isAvailable();
        boolean add(K key, int exp, V value);
        Optional<V> get(K key);
        Future<Boolean> flush();
        Map<SocketAddress, Map<String, String>> getStats();
}
