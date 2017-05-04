package com.aol.micro.server.elasticache;
import java.util.Optional;
/**
 * Created by gordonmorrow on 5/3/17.
 */
public interface DistributedCacheManager<V> {
        void setConnectionTested(boolean result);
        boolean isAvailable();
        boolean put(String key, int exp, V value);
        Optional<V> get(String key);
}
