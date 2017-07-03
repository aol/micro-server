package com.aol.micro.server.distributed;


import java.util.Optional;

public interface DistributedCache<V> {
    boolean put(String key, V value);
    Optional<V> get(String key);
    void delete(String key);
    boolean put(String key, int expiry, V value);
    boolean isAvailable();
    void setConnectionTested(boolean result);
}
