package com.aol.micro.server.application.metrics.jmx;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;

import cyclops.collections.MapX;
import cyclops.collections.SetX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
public class JmxMetricsAcquirer {

    private static final Logger logger = LoggerFactory.getLogger(JmxMetricsAcquirer.class);

    private final MBeanServer server = ManagementFactory.getPlatformMBeanServer();

    private final Map<String, MetricDescription> metricDescriptions;

    private final int period;

    private final TimeUnit timeUnit;

    private final MetricRegistry metricRegistry;

    @AllArgsConstructor
    @Getter
    private class MetricDescription {
        private final ObjectName objectName;
        private final String name;
        private final Function<Object, Long> converter;
    }

    private Function<Object, Long> memoryConverter(String name) {
        return o -> {
            CompositeData cd = (CompositeData) o;
            return (long) cd.get(name);
        };
    }

    private Function<Object, Long> threadsConverter() {
        return o -> {
            Number n = (Number) o;
            return n.longValue();
        };
    }

    private Map<String, MetricDescription> createMetricDescriptions(String excludedString) {
        MapX<String, MetricDescription> metricDescription = MapX.empty();

        ObjectName memory;
        try {
            memory = new ObjectName("java.lang:type=Memory");

            final ObjectName threads = new ObjectName("java.lang:type=Threading");

            metricDescription.put("jvm.heap_memory",
                    new MetricDescription(memory, "HeapMemoryUsage", memoryConverter("used")));

            metricDescription.put("jvm.heap_memory_committed",
                    new MetricDescription(memory, "HeapMemoryUsage", memoryConverter("committed")));

            metricDescription.put("jvm.heap_memory_init",
                    new MetricDescription(memory, "HeapMemoryUsage", memoryConverter("init")));

            metricDescription.put("jvm.heap_memory_max",
                    new MetricDescription(memory, "HeapMemoryUsage", memoryConverter("max")));

            metricDescription.put("jvm.non_heap_memory",
                    new MetricDescription(memory, "NonHeapMemoryUsage", memoryConverter("used")));

            metricDescription.put("jvm.non_heap_memory_committed",
                    new MetricDescription(memory, "NonHeapMemoryUsage", memoryConverter("committed")));

            metricDescription.put("jvm.non_heap_memory_init",
                    new MetricDescription(memory, "NonHeapMemoryUsage", memoryConverter("init")));

            metricDescription.put("jvm.non_heap_memory_max",
                    new MetricDescription(memory, "NonHeapMemoryUsage", memoryConverter("max")));

            metricDescription.put("jvm.thread_count",
                    new MetricDescription(threads, "ThreadCount", threadsConverter()));

        } catch (MalformedObjectNameException e) {
            logger.info("Can't initialize jmx metrics", e);
        }

        SetX<String> excluded = SetX.fromIterable(Arrays.asList(excludedString.split(",")));

        return metricDescription.filter(t -> !excluded.contains(t.v1));
    }

    @Autowired
    public JmxMetricsAcquirer(MetricRegistry metricRegistry, @Value("${datadog.report.period:1}") int period,
            @Value("${datadog.report.timeunit:SECONDS}") TimeUnit timeUnit,
            @Value("${jmx.metrics.excluded:}") String excluded) {
        this.metricRegistry = metricRegistry;
        this.period = period;
        this.timeUnit = timeUnit;
        this.metricDescriptions = createMetricDescriptions(excluded);
    }

    @PostConstruct
    public void init() {
        for (Map.Entry<String, MetricDescription> metric : metricDescriptions.entrySet()) {

            Metric m = new Gauge<Long>() {

                @Override
                public Long getValue() {
                    MetricDescription desc = metric.getValue();
                    Object o;
                    try {
                        o = server.getAttribute(desc.getObjectName(), desc.getName());
                        return metric.getValue().getConverter().apply(o);
                    } catch (AttributeNotFoundException | InstanceNotFoundException | MBeanException
                            | ReflectionException e) {
                        return null;
                    }

                }
            };

            metricRegistry.register(MetricRegistry.name(JmxMetricsAcquirer.class, metric.getKey(), metric.getKey()), m);
        }
    }

}
