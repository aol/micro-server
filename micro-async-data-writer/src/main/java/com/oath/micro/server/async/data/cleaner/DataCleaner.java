package com.oath.micro.server.async.data.cleaner;

import java.util.Random;
import java.util.function.Supplier;


import com.oath.micro.server.events.ScheduledJob;
import com.oath.micro.server.events.SystemData;
import com.oath.micro.server.manifest.ManifestComparator;
import com.oath.micro.server.utility.HashMapBuilder;

import cyclops.collections.mutable.MapX;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DataCleaner<T> implements ScheduledJob<T> {

    public static final String MANIFEST_COMPARATOR_DATA_CLEANER_KEY = "Manifest Comparator Data Cleaner";
    private final ManifestComparator<T> comparator;
    @Getter
    private final String cron;
    private final Random r = new Random();

    @Override
    public SystemData<String, String> scheduleAndLog() {
        String correlationId = "" + System.currentTimeMillis() + ":" + r.nextLong();
        Supplier<MapX<String, String>> dataMap = () -> MapX.fromMap(HashMapBuilder.map(MANIFEST_COMPARATOR_DATA_CLEANER_KEY,
                                                                                       comparator.toString())
                                                                                  .build());

        try {
            comparator.cleanAll();
            return SystemData.<String, String> builder()
                             .correlationId(correlationId)
                             .dataMap(dataMap.get())
                             .errors(0)
                             .processed(1)
                             .build();
        } catch (Exception e) {
            return SystemData.<String, String> builder()
                             .correlationId(correlationId)
                             .dataMap(dataMap.get()
                                             .plus("Error", e.getMessage()))
                             .errors(1)
                             .processed(0)
                             .build();
        }

    }

}