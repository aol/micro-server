package com.aol.micro.server.event.metrics;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

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
                                   true, true, true, true, 5, 6, 7, 8);
        catcher = new MetricsCatcher<>(
                                       registry, bus, config);
    }

    @Test
    public void testProcessing() {
        fail("Not yet implemented");
    }

    @Test
    public void testFinishedRemoveQueryOfT() {
        fail("Not yet implemented");
    }

    @Test
    public void testFinishedSystemData() {
        fail("Not yet implemented");
    }

    @Test
    public void testJobStarted() {
        fail("Not yet implemented");
    }

    @Test
    public void testJobComplete() {
        fail("Not yet implemented");
    }

    @Test
    public void testError() {
        fail("Not yet implemented");
    }

}
