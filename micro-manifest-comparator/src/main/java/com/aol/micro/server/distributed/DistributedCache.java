package com.aol.micro.server.distributed;


import java.util.Optional;

public interface DistributedCache<V> extends DistributedMap<V> {
    boolean put(String key, int expiry, V value);
    boolean isAvailable();
    void setConnectionTested(boolean result);
}
