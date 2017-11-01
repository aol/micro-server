package com.oath.micro.server.async.data.writer;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cyclops.collections.mutable.ListX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.oath.micro.server.manifest.ManifestComparator;
import com.google.common.eventbus.EventBus;

@Configuration
public class ConfigureDataWriter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired(required = false)
    private List<ManifestComparator> defaultComparators = ListX.empty();
    @Value("${async.data.writer.threads:1}")
    private int writerThreads = 1;
    @Value("${async.data.writer.multi:false}")
    private boolean multiWriterOn = false;

    @Autowired
    private EventBus bus;

    @Bean
    public AsyncDataWriter<?> defaultDataWriter() {
        if (defaultComparators.size() > 0) {
            System.err.println("Warning :: multiple ManifestComparators configured as Spring bean, using the first configured bean for the Default AsyncDataWriter, recommended approach is to configure your own DataWriters as needed.");
            logger.warn("Warning :: multiple ManifestComparators configured as Spring bean, using the first configured bean for the Default AsyncDataWriter, recommended approach is to configure your own DataWriters as needed.");
        }
        return new AsyncDataWriter(
                                   asyncDataWriterThreadPool(), defaultComparators.get(0), bus);
    }

    @Bean
    public MultiDataWriter<?> defaultMultiDataWriter() {
        if (multiWriterOn)
            return new MultiDataWriter(
                                       ListX.fromIterable(defaultComparators)
                                            .map(mc -> new AsyncDataWriter(
                                                                           asyncDataWriterThreadPool(), mc, bus)));
        return new MultiDataWriter(
                                   ListX.empty());
    }

    @Bean
    public Executor asyncDataWriterThreadPool() {
        return Executors.newFixedThreadPool(writerThreads);
    }

}
