package com.aol.micro.server.async.data.loader;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.BinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.aol.cyclops.data.collections.extensions.standard.SetX;
import com.aol.micro.server.manifest.ManifestComparator;
import com.google.common.eventbus.EventBus;

@Configuration
public class ConfigureScheduling {

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
                          .concat(dataLoaders.stream())
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
