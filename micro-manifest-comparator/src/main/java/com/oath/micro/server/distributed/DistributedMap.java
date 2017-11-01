package com.oath.micro.server.distributed;

import java.util.Optional;

public interface DistributedMap<V> {

    boolean put(String key, V value);

    Optional<V> get(String key);

    void delete(String key);

}