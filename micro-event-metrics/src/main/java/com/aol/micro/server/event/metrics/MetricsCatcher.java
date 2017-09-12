package com.aol.micro.server.event.metrics;

import com.aol.micro.server.events.GenericEvent;
import com.aol.micro.server.spring.metrics.InstantGauge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.errors.ErrorCode;
import com.aol.micro.server.events.JobCompleteEvent;
import com.aol.micro.server.events.JobStartEvent;
import com.aol.micro.server.events.RequestTypes.AddQuery;
import com.aol.micro.server.events.RequestTypes.RemoveQuery;
import com.aol.micro.server.events.RequestTypes.AddLabelledQuery;
import com.aol.micro.server.events.RequestTypes.RemoveLabelledQuery;
import com.aol.micro.server.events.RequestTypes.RequestData;
import com.aol.micro.server.events.SystemData;
import com.aol.micro.server.health.ErrorEvent;
import com.codahale.metrics.MetricRegistry;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.Objects;

@Component
public class MetricsCatcher<T> {

    private final MetricRegistry registry;

    private final TimerManager queries;

    private final TimerManager jobs;
    private final Configuration configuration;
    private final String prefix;

    @Autowired
    public MetricsCatcher(MetricRegistry registry, EventBus bus, Configuration configuration) {
        this.prefix = configuration.getPrefix();
        this.registry = registry;
        bus.register(this);
        queries = new TimerManager(
                                   configuration.getNumQueries(), configuration.getHoldQueriesForMinutes());
        jobs = new TimerManager(
                                configuration.getNumJobs(), configuration.getHoldJobsForMinutes());
        this.configuration = configuration;

    }

    @Subscribe
    public void requestStart(AddQuery<T> data) {
        registry.meter(prefix + ".requests-started")
                .mark();
        registry.counter(prefix + ".requests-started-count")
                .inc();
        ((InstantGauge) registry.gauge(prefix + ".requests-started-interval-count", () -> new InstantGauge())).increment();

        if (this.configuration.isQueriesByType()) {
            RequestData<T> rd = data.getData();

            registry.meter(queryStartName(rd) + "-meter")
                    .mark();

            queries.start(rd.getCorrelationId(), registry.timer(queryEndName(rd) + "-timer")
                                                         .time());
            registry.counter(prefix + ".requests-active-" + rd.getType() + "-count")
                    .inc();
            ((InstantGauge) registry.gauge(prefix + ".requests-started-" + rd.getType() + "-interval-count",
                    () -> new InstantGauge())).increment();

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
        registry.meter(prefix + ".requests-completed")
                .mark();
        registry.counter(prefix + ".requests-completed-count")
                .inc();
        ((InstantGauge) registry.gauge(prefix + ".requests-completed-interval-count", () -> new InstantGauge()))
                .increment();

        if (this.configuration.isQueriesByType()) {
            RequestData<T> rd = data.getData();
            registry.meter(queryEndName(rd))
                    .mark();

            queries.complete(rd.getCorrelationId());

            registry.counter(prefix + ".requests-active-" + rd.getType() + "-count")
                    .dec();
            ((InstantGauge) registry.gauge(prefix + ".requests-completed-" + rd.getType() + "-interval-count",
                    () -> new InstantGauge())).increment();

        }
    }

    @Subscribe
    public void requestStart(AddLabelledQuery<T> data) {
        if (this.configuration.isQueriesByType()) {
            RequestData<T> rd = data.getData();

            ((InstantGauge) registry.gauge(prefix + ".requests-started-" + rd.getType() + "-interval-count", () -> new InstantGauge()))
                    .increment();
        }
    }

    @Subscribe
    public void requestComplete(RemoveLabelledQuery<T> data) {
        if (this.configuration.isQueriesByType()) {
            RequestData<T> rd = data.getData();
            ((InstantGauge) registry.gauge(prefix + ".requests-completed-" + rd.getType() + "-interval-count", () -> new InstantGauge()))
                    .increment();
        }
    }

    @Subscribe
    public void finished(SystemData data) {
        registry.meter(prefix + ".jobs-completed")
                .mark();
        registry.counter(prefix + ".jobs-completed-count")
                .inc();

    }

    @Subscribe
    public void jobStarted(JobStartEvent data) {
        if (this.configuration.isJobsByType()) {
            registry.meter(prefix + ".job-meter-" + data.getType())
                    .mark();

            jobs.start(data.getCorrelationId(), registry.timer(prefix + ".job-timer-" + data.getType())
                                                        .time());
            registry.counter(prefix + ".jobs-active-" + data.getType() + "-count")
                    .inc();
        }
    }

    @Subscribe
    public void jobComplete(JobCompleteEvent data) {
        if (this.configuration.isJobsByType()) {
            jobs.complete(data.getCorrelationId());
            registry.counter(prefix + ".jobs-active-" + data.getType() + "-count")
                    .dec();

            registry.counter(prefix + ".jobs-processed-" + data.getType() + "-count-data")
                    .inc(data.getDataSize());
            registry.meter(prefix + ".jobs-processed-" + data.getType() + "-meter-data")
                    .mark(data.getDataSize());
            registry.counter(prefix + ".jobs-errors-" + data.getType() + "-count-data")
                    .inc(data.getErrors());
            registry.meter(prefix + ".jobs-errors-" + data.getType() + "-meter-data")
                    .mark(data.getErrors());

            if (data.getErrors() > 0l) {
                registry.counter(prefix + ".jobs-succeeded-" + data.getType() + "-count")
                        .inc();
                registry.meter(prefix + ".jobs-succeeded-" + data.getType() + "-meter")
                        .mark();

            } else {

                registry.counter(prefix + ".jobs-failed-" + data.getType() + "-count")
                        .inc();
                registry.meter(prefix + ".jobs-failed-" + data.getType() + "-meter")
                        .mark();
            }
        }
    }

    @Subscribe
    public void error(ErrorCode c) {
        registry.meter(prefix + ".errors")
                .mark();
        registry.counter(prefix + ".errors-count")
                .inc();
        if (this.configuration.isErrorsByCode()) {
            registry.meter(name(c))
                    .mark();
            registry.counter(name(c) + "-count")
                    .inc();
        }
        if (this.configuration.isErrorsByType()) {
            registry.meter(prefix + ".error-severity-" + c.getSeverity()
                                                          .name())
                    .mark();

            registry.counter(prefix + ".error-severity-" + c.getSeverity()
                                                            .name()
                    + "-count")
                    .inc();
        }
    }

    @Subscribe
    public void error(ErrorEvent event) {
        ErrorCode c = event.getCode();
        error(c);

    }

    @Subscribe
    public void genericEvent(GenericEvent event) {
        GenericEvent.GenericEventData eventData = event.getData();
        String name = prefix + ".event-" + eventData.getName();
        registry.counter(name + "-count").inc();
        registry.meter(name + "-meter").mark();
        if (Objects.nonNull(eventData.getSubTypes())) {
            for (String subType : eventData.getSubTypes()) {
                name = name + "." + subType;
                registry.counter(name + "-count").inc();
                registry.meter(name + "-meter").mark();
            }
        }
    }

    private String name(ErrorCode c) {
        return prefix + ".error-" + c.getSeverity() + "-" + c.getErrorId();
    }
}
