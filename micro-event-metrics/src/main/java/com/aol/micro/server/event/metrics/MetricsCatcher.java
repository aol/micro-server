package com.aol.micro.server.event.metrics;

import com.aol.micro.server.errors.ErrorCode;
import com.aol.micro.server.events.JobCompleteEvent;
import com.aol.micro.server.events.JobStartEvent;
import com.aol.micro.server.events.RequestsBeingExecuted.AddQuery;
import com.aol.micro.server.events.RequestsBeingExecuted.RemoveQuery;
import com.aol.micro.server.events.RequestsBeingExecuted.RequestData;
import com.aol.micro.server.events.SystemData;
import com.aol.micro.server.health.ErrorEvent;
import com.codahale.metrics.MetricRegistry;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricsCatcher<T> {

    public static final String prefix = MetricsCatcher.class.getTypeName();

    private final MetricRegistry registry;

    private final TimerManager queries;

    private final TimerManager jobs;
    private final Configuration configuration;

    @Autowired
    public MetricsCatcher(MetricRegistry registry, EventBus bus, Configuration configuration) {
        this.registry = registry;
        bus.register(this);
        queries = new TimerManager(configuration.getNumQueries(), configuration.getHoldQueriesForMinutes());
        jobs = new TimerManager(configuration.getNumJobs(), configuration.getHoldJobsForMinutes());
        this.configuration = configuration;
    }

    @Subscribe
    public void requestStart(AddQuery<T> data) {
        registry.meter(prefix + ".requests-started").mark();
        registry.counter(prefix + ".requests-started-count").inc();
        if (this.configuration.isQueriesByType()) {
            RequestData<T> rd = data.getData();

            registry.meter(queryStartName(rd)).mark();

            queries.start(rd.getCorrelationId(), registry.timer(queryEndName(rd) + "-timer").time());
            registry.counter(prefix + ".requests-active-" + rd.getType() + "-count").inc();
        }
    }

    private String queryStartName(RequestData<T> rd) {
        return prefix + ".request-start-" + rd.getType();
    }

    private String queryEndName(RequestData<T> rd) {
        return prefix + ".request-end-" + rd.getType();
    }

    @Subscribe
    public void requestComplete(RemoveQuery<T> data) {
        registry.meter(prefix + ".requests-completed").mark();
        registry.counter(prefix + ".requests-completed-count").inc();
        if (this.configuration.isQueriesByType()) {
            RequestData<T> rd = data.getData();
            registry.meter(queryEndName(rd)).mark();

            queries.complete(rd.getCorrelationId());

            registry.counter(prefix + ".requests-active-" + rd.getType() + "-count").dec();
        }

    }

    @Subscribe
    public void finished(SystemData data) {
        registry.meter(prefix + ".jobs-completed").mark();
        registry.counter(prefix + ".jobs-completed-count").inc();

    }

    @Subscribe
    public void jobStarted(JobStartEvent data) {
        if (this.configuration.isJobsByType()) {
            registry.meter(prefix + ".job-meter-" + data.getType()).mark();

            jobs.start(data.getCorrelationId(), registry.timer(prefix + ".job-timer-" + data.getType()).time());
            registry.counter(prefix + ".jobs-active-" + data.getType() + "-count").inc();
        }
    }

    @Subscribe
    public void jobComplete(JobCompleteEvent data) {
        if (this.configuration.isJobsByType()) {
            jobs.complete(data.getCorrelationId());
            registry.counter(prefix + ".jobs-active-" + data.getType() + "-count").dec();
        }
    }

    @Subscribe
    public void error(ErrorEvent event) {
        ErrorCode c = event.getCode();
        registry.meter(prefix + ".errors").mark();
        registry.counter(prefix + ".errors-count").inc();
        if (this.configuration.isErrorsByCode()) {
            registry.meter(name(c)).mark();
            registry.counter(name(c) + "-count").inc();
        }
        if (this.configuration.isErrorsByType()) {
            registry.meter(prefix + ".error-severity-" + c.getSeverity().name()).mark();

            registry.counter(prefix + ".error-severity-" + c.getSeverity().name() + "-count").inc();
        }

    }

    private String name(ErrorCode c) {
        return prefix + ".error-" + c.getSeverity() + "-" + c.getErrorId();
    }
}
