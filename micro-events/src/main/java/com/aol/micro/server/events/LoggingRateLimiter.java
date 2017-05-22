package com.aol.micro.server.events;

import java.util.Date;

import com.google.common.collect.ConcurrentHashMultiset;

import lombok.AccessLevel;
import lombok.Getter;

public class LoggingRateLimiter<T> {

    @Getter(AccessLevel.PACKAGE)
    private volatile ConcurrentHashMultiset<T> frequency = ConcurrentHashMultiset.create();
    private volatile Date lastCleared = new Date(
                                                 0);
    private final int limit;

    public LoggingRateLimiter(int limit) {
        this.limit = limit;
    }

    public LoggingRateLimiter() {
        this.limit = (60 * 1000 * 60);
    }

    public void addAndEnsureFrequency(T clazz) {
        resetAfterLimit();
        frequency.add(clazz);
    }

    public void resetAfterLimit() {
        if (System.currentTimeMillis() - limit > lastCleared.getTime()) {
            frequency = ConcurrentHashMultiset.create();
            lastCleared = new Date(
                                   System.currentTimeMillis());
        }

    }

    public void capacityAvailable(T t, long max, Runnable run) {
        if (frequency.count(t) < max)
            run.run();

    }

}
