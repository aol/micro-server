# Event Metrics Plugin


Captures Dropwizard metrics based on application events 

[micro-event-metrics example apps](https://github.com/aol/micro-server/tree/master/micro-event-metrics/src/test/java/app) 

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-event-metrics/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-event-metrics)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-event-metrics</artifactId>
        <version>x.yz</version>
     </dependency>
```   
Gradle
```groovy
    compile 'com.aol.microservices:micro-event-metrics:x.yz'
```
### Depends on

1. [micro-events](https://github.com/aol/micro-server/tree/master/micro-events)
1. [micro-error-codes](https://github.com/aol/micro-server/tree/master/micro-error-codes)
3. [micro-reactive](https://github.com/aol/micro-server/tree/master/micro-reactive)
4. [micro-guava](https://github.com/aol/micro-server/tree/master/micro-guava)

# Captures metrics on

1. Scheduled jobs
2. User requests
3. Errors


## Properties 

Job names are configurable as either 

* SIMPLE (simple class name)
* PACKAGE (last part of package + simple class name)
* FULL (canononical class name)

e.g.

    micro.events.job.name.format:SIMPLE
    
or

    micro.events.job.name.format:SIMPLE 

## The following metrics are captured

### Requests :

#### Meters :

```text
com.aol.micro.server.event.metrics.MetricsCatcher.requests-started
com.aol.micro.server.event.metrics.MetricsCatcher.request-start-<TYPE>
com.aol.micro.server.event.metrics.MetricsCatcher.requests-completed
com.aol.micro.server.event.metrics.MetricsCatcher.request-completed-<TYPE>
```

#### Timers :

```text
com.aol.micro.server.event.metrics.MetricsCatcher.request-completed-<TYPE>
```

#### Counters : 
```text
com.aol.micro.server.event.metrics.MetricsCatcher.requests-started-count
com.aol.micro.server.event.metrics.MetricsCatcher.requests-active-count
```

### Jobs :

#### Meters:
```text
com.aol.micro.server.event.metrics.MetricsCatcher.jobs-completed
com.aol.micro.server.event.metrics.MetricsCatcher.jobs-<TYPE>
```

#### Counters :
```text
com.aol.micro.server.event.metrics.MetricsCatcher.jobs-completed-count
com.aol.micro.server.event.metrics.MetricsCatcher.jobs-active-count
```

#### Timers :
```text
com.aol.micro.server.event.metrics.MetricsCatcher.job-timer-<TYPE>
```

### Errors:

#### Meters:
```text
com.aol.micro.server.event.metrics.MetricsCatcher.errors
com.aol.micro.server.event.metrics.MetricsCatcher.error-<SEVERITY>-<ERROR_CODE>
com.aol.micro.server.event.metrics.MetricsCatcher.error-<SEVERITY>
```

#### Counters:
```text
com.aol.micro.server.event.metrics.MetricsCatcher.errors-count
com.aol.micro.server.event.metrics.MetricsCatcher.error-<SEVERITY>-<ERROR_CODE>-count
com.aol.micro.server.event.metrics.MetricsCatcher.error-<SEVERITY>-count
```

# Configuration

Configuration properties and their default values

```text
event.metrics.capture.errors.by.type=true  # errorsByType,
event.metrics.capture.errors.by.code=true # errorsByCode,
event.metrics.capture.queries.by.type=true # queriesByType,
event.metrics.capture.jobs.by.type=true # jobsByType,
event.metrics.capture.number.of.queries=10000 # numQueries,
event.metrics.capture.queries.minutes=180 # holdQueriesForMinutes,
event.metrics.capture.number.of.jobs=10000 # numJobs,
event.metrics.capture.jobs.minutes=180
```

