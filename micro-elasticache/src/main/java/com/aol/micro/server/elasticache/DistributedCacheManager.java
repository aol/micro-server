package com.aol.micro.server.elasticache;
import java.util.Optional;

public interface DistributedCacheManager<V> {
        void setConnectionTested(boolean result);
        boolean isAvailable();
        boolean add(String key, int exp, Object value);
        Optional<V> get(String key);
}
