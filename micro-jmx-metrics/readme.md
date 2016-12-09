# JMX Metrics


This plugin expose JMX metrics to metricRegistry, so they can be sent to datadog using by micro-metrics-datadog.

Main advantage that it don't use remote JMX connection, so you still can connect using VisualVM/JConsole to your applications.

See also [micro-metrics-datadog](https://github.com/aol/micro-server/tree/master/micro-metrics-datadog) 

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-jmx-metrics/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-jmx-metrics)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-jmx-metrics</artifactId>
        <version>x.yz</version>
     </dependency>
```   
Gradle
```groovy
    compile 'com.aol.microservices:micro-jmx-metrics:x.yz'
```

Metrics reported by this plugin:
* jvm.heap_memory - heap memory used
* jvm.heap_memory_committed - heap memory committed
* jvm.heap_memory_init - heap memory init
* jvm.heap_memory_max - heap memory max
* jvm.non_heap_memory - non-heap memory used
* jvm.non_heap_memory_committed - non-heap memory committed
* jvm.non_heap_memory_init - non-heap memory init
* jvm.non_heap_memory_max - non-heap memory max
* jvm.thread_count - number of threads.

Property *jmx.metrics.excluded* may be set to comma-separated list of those metrics. Metrics included in this list will not be reported to datadog.
Datadog metrics will have prefix *com.aol.micro.server.application.metrics.jmx.JmxMetricsAcquirer.*
