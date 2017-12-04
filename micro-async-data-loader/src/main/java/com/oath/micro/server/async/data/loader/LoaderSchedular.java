package com.oath.micro.server.async.data.loader;

import java.util.concurrent.ScheduledExecutorService;


import com.oath.micro.server.events.SystemData;
import com.google.common.eventbus.EventBus;

import cyclops.collections.mutable.ListX;
import cyclops.reactive.ReactiveSeq;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoaderSchedular {

    private final ScheduledExecutorService executor;
    private final EventBus bus;
    private final ConditionallyLoad condition;
    private ListX<DataLoader> loader;

    public void schedule() {

        loader.forEach(dl -> {

            // run on startup
            create(dl).limit(1).foldFuture(executor, s -> s.forEach(Long.MAX_VALUE, l -> {}));

            // schedule
            create(dl).schedule(dl.getCron(), executor);
        });
    }

    private ReactiveSeq<SystemData<String, String>> create(DataLoader dl) {
        return ReactiveSeq.generate(() -> 1)
            .filter(in -> condition.shouldLoad())
            .map(in -> dl.scheduleAndLog())
            .peek(sd -> bus.post(sd));
    }
}
