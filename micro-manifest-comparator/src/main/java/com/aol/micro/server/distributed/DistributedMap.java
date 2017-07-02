package com.aol.micro.server.distributed;

import java.util.Optional;

public interface DistributedMap<V> {

    boolean put(String key, V value);
    Optional<V> get(String key);
    void delete(String key);
    default boolean put(String key, int expiry, V value){
        return false;
    }

}