package com.aol.micro.server.datadog.metrics;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import org.coursera.metrics.datadog.DatadogReporter;
import org.junit.Test;

public class DatadogMetricsConfigurerTest {

    private String apiKey = "api";
    private String tags = "tags";
    private int period = 10;
    private TimeUnit timeUnit = TimeUnit.DAYS;

    @Test
    public void expansionsDefault() {
        String expStr = null;
        DatadogMetricsConfigurer c = new DatadogMetricsConfigurer(
                                                                  apiKey, tags, period, timeUnit, expStr);
        assertThat(c.getExpansions(), equalTo(DatadogReporter.Expansion.ALL));
    }

    @Test
    public void expansionsSingle() {
        String expStr = DatadogReporter.Expansion.MEDIAN.name();
        DatadogMetricsConfigurer c = new DatadogMetricsConfigurer(
                                                                  apiKey, tags, period, timeUnit, expStr);
        assertThat(c.getExpansions(), equalTo(EnumSet.of(DatadogReporter.Expansion.MEDIAN)));
    }

    @Test
    public void expansionsTwo() {
        String expStr = DatadogReporter.Expansion.MEDIAN.name() + "," + DatadogReporter.Expansion.RATE_15_MINUTE.name();
        DatadogMetricsConfigurer c = new DatadogMetricsConfigurer(
                                                                  apiKey, tags, period, timeUnit, expStr);
        assertThat(c.getExpansions(),
                   equalTo(EnumSet.of(DatadogReporter.Expansion.MEDIAN, DatadogReporter.Expansion.RATE_15_MINUTE)));
    }

    @Test
    public void expansionsTwoSpace() {
        String expStr = DatadogReporter.Expansion.MEDIAN.name() + " , "
                + DatadogReporter.Expansion.RATE_15_MINUTE.name();
        DatadogMetricsConfigurer c = new DatadogMetricsConfigurer(
                                                                  apiKey, tags, period, timeUnit, expStr);
        assertThat(c.getExpansions(),
                   equalTo(EnumSet.of(DatadogReporter.Expansion.MEDIAN, DatadogReporter.Expansion.RATE_15_MINUTE)));
    }

}
