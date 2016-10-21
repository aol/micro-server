package com.aol.micro.server.event.metrics;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.errors.ErrorCode;
import com.aol.micro.server.events.JobCompleteEvent;
import com.aol.micro.server.events.JobStartEvent;
import com.aol.micro.server.events.RequestTypes.AddQuery;
import com.aol.micro.server.events.RequestTypes.RemoveQuery;
import com.aol.micro.server.events.RequestTypes.RequestData;
import com.codahale.metrics.MetricRegistry;
import com.google.common.eventbus.EventBus;

public class MetricsCatcherTest {

    MetricsCatcher<?> catcher;
    MetricRegistry registry;
    EventBus bus;
    Configuration config;

    @Before
    public void setup() {
        registry = new MetricRegistry();
        bus = new EventBus();
        config = new Configuration(
                                   true, true, true, true, 5, 6, 7, 8, "bob");
        catcher = new MetricsCatcher<>(
                                       registry, bus, config);
    }

    @Test
    public void queriesStartMeterInc() {

        catcher.requestStart(new AddQuery(
                                          RequestData.builder()
                                                     .correlationId(10l)
                                                     .type("test")
                                                     .build()));
        assertThat(registry.meter(this.config.getPrefix() + ".request-start-test")
                           .getMeanRate(),
                   greaterThan(0.0));
    }

    @Test
    public void queriesEndMeterInc() {

        catcher.requestComplete(new RemoveQuery(
                                                RequestData.builder()
                                                           .correlationId(10l)
                                                           .type("test")
                                                           .build()));
        assertThat(registry.meter(this.config.getPrefix() + ".request-end-test")
                           .getMeanRate(),
                   greaterThan(0.0));
    }

    @Test
    public void queriesCounterInc() {

        catcher.requestStart(new AddQuery(
                                          RequestData.builder()
                                                     .correlationId(10l)
                                                     .type("test")
                                                     .build()));
        assertThat(registry.counter(this.config.getPrefix() + ".requests-active-test-count")
                           .getCount(),
                   equalTo(1l));
    }

    @Test
    public void queriesCounterDec() {

        catcher.requestComplete(new RemoveQuery(
                                                RequestData.builder()
                                                           .correlationId(10l)
                                                           .type("test")
                                                           .build()));
        assertThat(registry.counter(this.config.getPrefix() + ".requests-active-test-count")
                           .getCount(),
                   equalTo(-1l));
    }

    @Test
    public void jobsCounterDec() {

        catcher.jobComplete(new JobCompleteEvent(
                                                 10l, "test"));
        assertThat(registry.counter(this.config.getPrefix() + ".jobs-active-test-count")
                           .getCount(),
                   equalTo(-1l));
    }

    @Test
    public void queriesTimer() {

        catcher.requestStart(new AddQuery(
                                          RequestData.builder()
                                                     .correlationId(10l)
                                                     .type("test")
                                                     .build()));

        catcher.requestComplete(new RemoveQuery(
                                                RequestData.builder()
                                                           .correlationId(10l)
                                                           .type("test")
                                                           .build()));
        assertThat(registry.timer(this.config.getPrefix() + ".request-end-test-timer")
                           .getMeanRate(),
                   greaterThan(0.0));
    }

    @Test
    public void jobsMeterInc() {

        catcher.jobStarted(new JobStartEvent(
                                             10l, "test"));
        assertThat(registry.meter(this.config.getPrefix() + ".job-meter-test")
                           .getMeanRate(),
                   greaterThan(0.0));
    }

    @Test
    public void jobsCounterInc() {

        catcher.jobStarted(new JobStartEvent(
                                             10l, "test"));
        assertThat(registry.counter(this.config.getPrefix() + ".jobs-active-test-count")
                           .getCount(),
                   equalTo(1l));
    }

    @Test
    public void testErrorCount() {
        catcher.error(ErrorCode.medium(10, "hello world"));
        assertThat(registry.counter(this.config.getPrefix() + ".error-MEDIUM-10-count")
                           .getCount(),
                   equalTo(1l));

    }

    @Test
    public void testErrorMeter() {
        catcher.error(ErrorCode.medium(10, "hello world"));
        assertThat(registry.meter(this.config.getPrefix() + ".error-MEDIUM-10")
                           .getMeanRate(),
                   greaterThan(0.00));

    }

    @Test
    public void testSeverityErrorCount() {
        catcher.error(ErrorCode.medium(10, "hello world"));
        assertThat(registry.counter(this.config.getPrefix() + ".error-severity-MEDIUM-count")
                           .getCount(),
                   equalTo(1l));

    }

    @Test
    public void testErrorSeverityMeter() {
        catcher.error(ErrorCode.medium(10, "hello world"));
        assertThat(registry.meter(this.config.getPrefix() + ".error-severity-MEDIUM")
                           .getMeanRate(),
                   greaterThan(0.00));

    }

}
