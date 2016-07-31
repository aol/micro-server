package com.aol.micro.server.event.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.errors.ErrorCode;
import com.aol.micro.server.events.JobCompleteEvent;
import com.aol.micro.server.events.JobStartEvent;
import com.aol.micro.server.events.RequestsBeingExecuted.AddQuery;
import com.aol.micro.server.events.RequestsBeingExecuted.RemoveQuery;
import com.aol.micro.server.events.RequestsBeingExecuted.RequestData;
import com.aol.micro.server.events.SystemData;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Metered;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import lombok.Getter;
import lombok.Setter;

@Component
public class QueryMetrics<T> {

    private final MetricRegistry registry;

    private final TimerManager queries = new TimerManager(
                                                          10_000, 180);

    private final TimerManager jobs = new TimerManager(
                                                       10_000, 180);
    @Getter
    @Setter
    private volatile boolean errorsByType = true;
    @Getter
    @Setter
    private volatile boolean errorsByCode = true;
    @Getter
    @Setter
    private volatile boolean queriesByType = true;
    @Getter
    @Setter
    private volatile boolean jobsByType = true;

    @Autowired
    public QueryMetrics(MetricRegistry registry, EventBus bus) {
        this.registry = registry;
        bus.register(this);
    }

    @Subscribe
    @Metered(name = "requests-started")
    @Counted(name = "request-started-count")
    public void processing(AddQuery<T> data) {
        RequestData<T> rd = data.getData();

        registry.meter(queryStartName(rd))
                .mark();

        queries.start(rd.getCorrelationId(), registry.timer(queryEndName(rd))
                                                     .time());
        registry.counter("queries-active-" + rd.getCorrelationId() + "-count")
                .inc();
    }

    private String queryStartName(RequestData<T> rd) {
        return "query-start-" + rd.getType();
    }

    private String queryEndName(RequestData<T> rd) {
        return "query-end-" + rd.getType();
    }

    @Metered(name = "requests-completed")
    @Counted(name = "request-completed-count")
    @Subscribe
    public void finished(RemoveQuery<T> data) {
        RequestData<T> rd = data.getData();
        registry.meter(queryEndName(rd))
                .mark();

        queries.complete(rd.getCorrelationId());
        registry.counter("queries-active-" + rd.getCorrelationId() + "-count")
                .dec();

    }

    @Counted(name = "jobs-completed-count")
    @Metered(name = "jobs-completed")
    @Subscribe
    public void finished(SystemData data) {

    }

    @Subscribe
    public void jobStarted(JobStartEvent data) {
        registry.meter("job-meter-" + data.getType())
                .mark();

        jobs.start(data.getCorrelationId(), registry.timer("job-timer-" + data.getType())
                                                    .time());
        registry.counter("jobs-active-" + data.getType() + "-count")
                .inc();
    }

    @Subscribe
    public void jobComplete(JobCompleteEvent data) {

        jobs.complete(data.getCorrelationId());
        registry.counter("jobs-active-" + data.getType() + "-count")
                .dec();
    }

    @Metered(name = "errors")
    @Counted(name = "errors-count")
    @Subscribe
    public void error(ErrorCode c) {
        registry.meter(name(c))
                .mark();
        registry.meter("error-severity-" + c.getSeverity()
                                            .name())
                .mark();
        registry.counter(name(c) + "-count")
                .inc();
        registry.counter("error-severity-" + c.getSeverity()
                                              .name()
                + "-count")
                .inc();
        ;
    }

    private String name(ErrorCode c) {
        return "error-" + c.getSeverity() + "-" + c.getErrorId();
    }
}
