package com.aol.micro.server.event.metrics;

import java.util.concurrent.TimeUnit;

import com.aol.cyclops.control.Maybe;
import com.codahale.metrics.Timer.Context;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class TimerManager {

    private final Cache<Long, Context> contexts;

    public TimerManager(long maxSize, int minutesUntilExpire) {
        contexts = CacheBuilder.newBuilder()
                               .maximumSize(maxSize)
                               .expireAfterWrite(minutesUntilExpire, TimeUnit.MINUTES)
                               .build();
    }

    public void complete(long id) {
        Maybe.ofNullable(contexts.getIfPresent(id))
             .forEach(c -> c.stop());
        contexts.invalidate(id);
    }

    public void start(long id, Context context) {
        contexts.put(id, context);

    }
}
