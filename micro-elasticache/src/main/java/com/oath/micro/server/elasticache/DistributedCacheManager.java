package com.oath.micro.server.elasticache;
import java.util.Optional;

public interface DistributedCacheManager<V> {
        void setConnectionTested(boolean result);
        boolean isAvailable();
        boolean add(String key, int exp, V value);
        Optional<V> get(String key);
}
