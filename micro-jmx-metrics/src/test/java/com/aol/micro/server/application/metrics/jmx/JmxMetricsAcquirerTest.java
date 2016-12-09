package com.aol.micro.server.application.metrics.jmx;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.codahale.metrics.MetricRegistry;

public class JmxMetricsAcquirerTest {

    @Test
    public void test() {

        MetricRegistry metricRegistry = mock(MetricRegistry.class);

        JmxMetricsAcquirer acquirer = new JmxMetricsAcquirer(metricRegistry, 1, TimeUnit.SECONDS,
                "jvm.heap_memory_max,jvm.non_heap_memory_max");

        acquirer.init();

        verify(metricRegistry, times(0)).register(eq("jvm.heap_memory_max"), any());
        verify(metricRegistry, times(0)).register(eq("jvm.non_heap_memory_max"), any());

    }
}
