package com.aol.micro.server.async.data.cleaner;

import java.util.concurrent.ScheduledExecutorService;


import com.google.common.eventbus.EventBus;

import cyclops.collections.mutable.ListX;
import cyclops.reactive.ReactiveSeq;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CleanerSchedular {

    private final ListX<DataCleaner> cleaner;
    private final ScheduledExecutorService executor;
    private final EventBus bus;
    private final ConditionallyClean condition;

    public void schedule() {
        cleaner.forEach(cl -> {
            ReactiveSeq.generate(() -> 1)
                       .filter(in -> condition.shouldClean())
                       .map(i -> cl.scheduleAndLog())
                       .peek(sd -> bus.post(sd))
                       .schedule(cl.getCron(), executor);
        });
    }
}
