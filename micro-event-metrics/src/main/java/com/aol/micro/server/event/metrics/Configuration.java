package com.aol.micro.server.event.metrics;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
class Configuration {

    private volatile boolean errorsByType = true;
    private volatile boolean errorsByCode = true;
    private volatile boolean queriesByType = true;
    private volatile boolean jobsByType = true;

    private final int numQueries;
    private final int holdQueriesForMinutes;

    private final int numJobs;
    private final int holdJobsForMinutes;
    private final String prefix;

    @Autowired
    public Configuration(@Value("${event.metrics.capture.errors.by.type:true}") boolean errorsByType,
            @Value("${event.metrics.capture.errors.by.code:true}") boolean errorsByCode,
            @Value("${event.metrics.capture.queries.by.type:true}") boolean queriesByType,
            @Value("${event.metrics.capture.jobs.by.type:true}") boolean jobsByType,
            @Value("${event.metrics.capture.number.of.queries:10000}") int numQueries,
            @Value("${event.metrics.capture.queries.minutes:180}") int holdQueriesForMinutes,
            @Value("${event.metrics.capture.number.of.jobs:10000}") int numJobs,
            @Value("${event.metrics.capture.jobs.minutes:180}") int holdJobsForMinutes,
            @Value("${event.metrics.capture.jobs.prefix:#{null}}") String prefix) {
        super();
        this.errorsByType = errorsByType;
        this.errorsByCode = errorsByCode;
        this.queriesByType = queriesByType;
        this.jobsByType = jobsByType;
        this.numQueries = numQueries;
        this.holdQueriesForMinutes = holdQueriesForMinutes;
        this.numJobs = numJobs;
        this.holdJobsForMinutes = holdJobsForMinutes;
        this.prefix = Optional.ofNullable(prefix)
                              .orElseGet(() -> MetricsCatcher.class.getTypeName());
    }

}
