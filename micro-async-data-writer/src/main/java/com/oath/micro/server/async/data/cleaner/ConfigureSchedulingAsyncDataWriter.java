package com.oath.micro.server.async.data.cleaner;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.BinaryOperator;

import cyclops.collections.mutable.ListX;
import cyclops.control.Maybe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.oath.micro.server.manifest.ManifestComparator;
import com.google.common.eventbus.EventBus;

@Configuration
public class ConfigureSchedulingAsyncDataWriter {

    @Value("${async.data.schedular.cron.cleaner:0 0 * * * ?}")
    private String defaultCronCleaner;
    @Value("${async.data.schedular.threads:1}")
    private int schedularThreads;

    @Autowired(required = false)
    private List<DataCleaner> dataCleaners = ListX.empty();
    @Autowired(required = false)
    private List<ConditionallyClean> predicates = ListX.empty();

    @Autowired
    private EventBus bus;

    @Autowired(required = false)
    private List<ManifestComparator> defaultComparators;

    private ListX<DataCleaner> dataCleaners() {
        Maybe<DataCleaner> defaultDataCleaner = defaultComparators.size() == 1 ? Maybe.just(new DataCleaner(
                                                                                                            defaultComparators.get(0),
                                                                                                            defaultCronCleaner))
                : Maybe.nothing();
        return ListX.fromIterable(defaultDataCleaner)
                    .plusAll(dataCleaners);

    }

    @Bean
    public CleanerSchedular asyncDataCleaner() {
        ConditionallyClean cc = () -> true;
        BinaryOperator<ConditionallyClean> accumulator = (cc1, cc2) -> () -> cc1.shouldClean() && cc2.shouldClean();
        CleanerSchedular schedular = new CleanerSchedular(
                                                          dataCleaners(),
                                                          Executors.newScheduledThreadPool(schedularThreads), bus,
                                                          predicates.stream()
                                                                    .reduce(cc, accumulator));
        schedular.schedule();
        return schedular;
    }
}
