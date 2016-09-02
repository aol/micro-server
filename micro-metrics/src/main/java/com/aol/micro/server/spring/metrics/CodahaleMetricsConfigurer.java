package com.aol.micro.server.spring.metrics;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import lombok.Setter;

@Configuration
@EnableMetrics
public class CodahaleMetricsConfigurer extends MetricsConfigurerAdapter {

    @Setter
    private static volatile Consumer<MetricRegistry> init = (metricRegistry) -> {

        JmxReporter.forRegistry(metricRegistry)
                   .build()
                   .start();

        ConsoleReporter.forRegistry(metricRegistry)
                       .build()
                       .start(1, TimeUnit.HOURS);
    };

    public static void switchOff() {
        setInit((m) -> {
        });
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        CodahaleMetricsConfigurer.init.accept(metricRegistry);
    }

}