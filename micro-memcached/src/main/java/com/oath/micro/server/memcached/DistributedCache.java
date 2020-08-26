package com.oath.micro.server.memcached;
import java.util.Optional;

public interface DistributedCache<K, V> {
        void setConnectionTested(boolean result);
        boolean isAvailable();
        boolean add(K key, int exp, V value);
        Optional<V> get(K key);
}
