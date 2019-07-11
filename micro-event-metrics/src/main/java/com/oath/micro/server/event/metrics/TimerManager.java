package com.oath.micro.server.event.metrics;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Timer.Context;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import cyclops.control.Maybe;

public class TimerManager {

    private final Cache<String, Context> contexts;

    public TimerManager(long maxSize, int minutesUntilExpire) {
        contexts = CacheBuilder.newBuilder()
                               .maximumSize(maxSize)
                               .expireAfterWrite(minutesUntilExpire, TimeUnit.MINUTES)
                               .build();
    }

    public void complete(String id) {
        Maybe.ofNullable(contexts.getIfPresent(id))
             .forEach(Context::stop);
        contexts.invalidate(id);
    }

    public void start(String id, Context context) {
        contexts.put(id, context);

    }
}
