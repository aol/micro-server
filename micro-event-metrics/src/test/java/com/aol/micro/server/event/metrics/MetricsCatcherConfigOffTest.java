package com.aol.micro.server.event.metrics;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.errors.ErrorCode;
import com.aol.micro.server.events.JobCompleteEvent;
import com.aol.micro.server.events.JobStartEvent;
import com.aol.micro.server.events.RequestsBeingExecuted.AddQuery;
import com.aol.micro.server.events.RequestsBeingExecuted.RemoveQuery;
import com.aol.micro.server.events.RequestsBeingExecuted.RequestData;
import com.codahale.metrics.MetricRegistry;
import com.google.common.eventbus.EventBus;

public class MetricsCatcherConfigOffTest {

    MetricsCatcher<?> catcher;
    MetricRegistry registry;
    EventBus bus;
    Configuration config;

    @Before
    public void setup() {
        registry = new MetricRegistry();
        bus = new EventBus();
        config = new Configuration(
                                   false, false, false, false, 5, 6, 7, 8);
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
        assertThat(registry.meter("request-start-test")
                           .getMeanRate(),
                   equalTo(0.0));
    }

    @Test
    public void queriesEndMeterInc() {

        catcher.requestComplete(new RemoveQuery(
                                                RequestData.builder()
                                                           .correlationId(10l)
                                                           .type("test")
                                                           .build()));
        assertThat(registry.meter("request-end-test")
                           .getMeanRate(),
                   equalTo(0.0));
    }

    @Test
    public void queriesCounterInc() {

        catcher.requestStart(new AddQuery(
                                          RequestData.builder()
                                                     .correlationId(10l)
                                                     .type("test")
                                                     .build()));
        assertThat(registry.counter("requests-active-test-count")
                           .getCount(),
                   equalTo(0l));
    }

    @Test
    public void queriesCounterDec() {

        catcher.requestComplete(new RemoveQuery(
                                                RequestData.builder()
                                                           .correlationId(10l)
                                                           .type("test")
                                                           .build()));
        assertThat(registry.counter("requests-active-test-count")
                           .getCount(),
                   equalTo(0l));
    }

    @Test
    public void jobsCounterDec() {

        catcher.jobComplete(new JobCompleteEvent(
                                                 10l, "test"));
        assertThat(registry.counter("jobs-active-test-count")
                           .getCount(),
                   equalTo(0l));
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
        assertThat(registry.timer("request-end-test-timer")
                           .getMeanRate(),
                   equalTo(0.0));
    }

    @Test
    public void jobsMeterInc() {

        catcher.jobStarted(new JobStartEvent(
                                             10l, "test"));
        assertThat(registry.meter("job-meter-test")
                           .getMeanRate(),
                   equalTo(0.0));
    }

    @Test
    public void jobsCounterInc() {

        catcher.jobStarted(new JobStartEvent(
                                             10l, "test"));
        assertThat(registry.counter("jobs-active-test-count")
                           .getCount(),
                   equalTo(0l));
    }

    @Test
    public void testErrorCount() {
        catcher.error(ErrorCode.medium(10, "hello world"));
        assertThat(registry.counter("error-MEDIUM-10-count")
                           .getCount(),
                   equalTo(0l));

    }

    @Test
    public void testErrorMeter() {
        catcher.error(ErrorCode.medium(10, "hello world"));
        assertThat(registry.meter("error-MEDIUM-10")
                           .getMeanRate(),
                   equalTo(0.00));

    }

    @Test
    public void testSeverityErrorCount() {
        catcher.error(ErrorCode.medium(10, "hello world"));
        assertThat(registry.counter("error-severity-MEDIUM-count")
                           .getCount(),
                   equalTo(0l));

    }

    @Test
    public void testErrorSeverityMeter() {
        catcher.error(ErrorCode.medium(10, "hello world"));
        assertThat(registry.meter("error-severity-MEDIUM")
                           .getMeanRate(),
                   equalTo(0.00));

    }

}
