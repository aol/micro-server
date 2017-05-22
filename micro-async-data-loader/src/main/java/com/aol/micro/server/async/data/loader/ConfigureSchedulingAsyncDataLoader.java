package com.aol.micro.server.async.data.loader;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.BinaryOperator;

import cyclops.collections.ListX;
import cyclops.collections.SetX;
import cyclops.stream.ReactiveSeq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.aol.micro.server.manifest.ManifestComparator;
import com.google.common.eventbus.EventBus;

@Configuration
public class ConfigureSchedulingAsyncDataLoader {

    @Value("${async.data.schedular.cron.loader:0 * * * * ?}")
    private String defaultCron;

    @Value("${async.data.schedular.threads:5}")
    private int schedularThreads;

    @Autowired(required = false)
    private List<DataLoader> dataLoaders = ListX.empty();

    @Autowired
    private EventBus bus;

    @Autowired(required = false)
    private List<ManifestComparator> defaultComparators = ListX.empty();
    @Autowired(required = false)
    private List<ConditionallyLoad> predicates = ListX.empty();

    private ListX<DataLoader> dataLoaders() {
        SetX<ManifestComparator> comparatorSet = SetX.fromIterable(dataLoaders)
                                                     .map(dl -> dl.comparator);
        return ReactiveSeq.fromIterable(defaultComparators)
                          .filter(i -> !comparatorSet.contains(i))
                          .map(mc -> new DataLoader(
                                                    mc, defaultCron))
                          .appendS(dataLoaders.stream())
                          .toListX();

    }

    @Bean
    public LoaderSchedular asyncDataLoader() {
        ConditionallyLoad cc = () -> true;
        BinaryOperator<ConditionallyLoad> accumulator = (cc1, cc2) -> () -> cc1.shouldLoad() && cc2.shouldLoad();

        LoaderSchedular schedular = new LoaderSchedular(
                                                        dataLoaders(),
                                                        Executors.newScheduledThreadPool(schedularThreads), bus,
                                                        predicates.stream()
                                                                  .reduce(cc, accumulator));
        schedular.schedule();
        return schedular;
    }

}
