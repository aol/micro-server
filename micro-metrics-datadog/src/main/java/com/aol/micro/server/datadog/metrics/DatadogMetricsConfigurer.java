package com.aol.micro.server.datadog.metrics;

import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.coursera.metrics.datadog.DatadogReporter;
import org.coursera.metrics.datadog.transport.HttpTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.coursera.metrics.datadog.DatadogReporter.Expansion.COUNT;

@Configuration
@EnableMetrics
public class DatadogMetricsConfigurer extends MetricsConfigurerAdapter {

    private String apiKey;
    private List<String> tags;
    private int period;
    private TimeUnit timeUnit;

    @Autowired
    public DatadogMetricsConfigurer(@Value("${datadog.apikey}") String apiKey,
            @Value("${datadog.tags:{\"stage:dev\"}}") String tags, @Value("${datadog.report.period:1}") int period,
            @Value("${datadog.report.timeunit:SECONDS}") TimeUnit timeUnit) {
        this.apiKey = apiKey;
        this.tags = Arrays.asList(Optional.ofNullable(tags).orElse("").split(","));
        this.period = period;
        this.timeUnit = timeUnit;
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        HttpTransport httpTransport = new HttpTransport.Builder().withApiKey(apiKey).build();
        EnumSet<DatadogReporter.Expansion> expansions = EnumSet.of(COUNT);
        DatadogReporter reporter = DatadogReporter.forRegistry(metricRegistry)
                                                  .withTransport(httpTransport)
                                                  .withExpansions(expansions)
                                                  .withTags(tags)
                                                  .build();
        reporter.start(period, timeUnit);
        registerReporter(reporter);
    }
}