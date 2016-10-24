package com.aol.micro.server.datadog.metrics;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.coursera.metrics.datadog.DatadogReporter;
import org.coursera.metrics.datadog.transport.HttpTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.aol.cyclops.control.Maybe;
import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import lombok.Getter;

@Configuration
@EnableMetrics
public class DatadogMetricsConfigurer extends MetricsConfigurerAdapter {

    private String apiKey;
    private List<String> tags;
    private int period;
    private TimeUnit timeUnit;
    @Getter
    private EnumSet<DatadogReporter.Expansion> expansions;

    @Autowired
    public DatadogMetricsConfigurer(@Value("${datadog.apikey}") String apiKey,
            @Value("${datadog.tags:{\"stage:dev\"}}") String tags, @Value("${datadog.report.period:1}") int period,
            @Value("${datadog.report.timeunit:SECONDS}") TimeUnit timeUnit,
            @Value("${datadog.report.expansions:#{null}}") String expStr) {
        this.apiKey = apiKey;
        this.tags = Arrays.asList(Optional.ofNullable(tags)
                                          .orElse("")
                                          .split(","));
        this.period = period;
        this.timeUnit = timeUnit;
        this.expansions = expansions(expStr);
    }

    private EnumSet<DatadogReporter.Expansion> expansions(String expStr) {
        return Maybe.just(Maybe.ofNullable(expStr)
                               .map(s -> s.split(","))
                               .stream()
                               .flatMap(Stream::of)
                               .map(String::trim)
                               .map(DatadogReporter.Expansion::valueOf)
                               .collect(Collectors.toCollection(() -> EnumSet.noneOf(DatadogReporter.Expansion.class))))
                    .filter(s -> !s.isEmpty())
                    .orElse(DatadogReporter.Expansion.ALL);
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        HttpTransport httpTransport = new HttpTransport.Builder().withApiKey(apiKey)
                                                                 .build();
        EnumSet<DatadogReporter.Expansion> expansions = DatadogReporter.Expansion.ALL;
        DatadogReporter reporter = DatadogReporter.forRegistry(metricRegistry)
                                                  .withTransport(httpTransport)
                                                  .withExpansions(expansions)
                                                  .withTags(tags)
                                                  .build();
        reporter.start(period, timeUnit);
        registerReporter(reporter);
    }
}