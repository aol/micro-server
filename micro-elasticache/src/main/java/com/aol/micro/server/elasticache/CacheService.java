package com.aol.micro.server.elasticache;

import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.Future;

/**
 * Created by gordonmorrow on 5/3/17.
 */
public interface CacheService {
    @Async("distributedCacheTaskExecutor")
    public abstract Future<Boolean> put(String key, Object o);

    public abstract Optional<Object> get(String key);

    public abstract boolean isAvailable();

}
