package com.aol.micro.server.async.data.loader;

import java.util.concurrent.ScheduledExecutorService;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.aol.micro.server.events.SystemData;
import com.google.common.eventbus.EventBus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoaderSchedular {

    private ListX<DataLoader> loader;
    private final ScheduledExecutorService executor;
    private final EventBus bus;

    public void schedule() {

        loader.forEach(dl -> {

            // run on startup
            create(dl).limit(1)
                      .futureOperations(executor)
                      .forEach(l -> {
            });

            // schedule
            create(dl).schedule(dl.getCron(), executor);
        });
    }

    private ReactiveSeq<SystemData<String, String>> create(DataLoader dl) {
        return ReactiveSeq.generate(() -> dl.scheduleAndLog())
                          .peek(sd -> bus.post(sd));
    }
}
