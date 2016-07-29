package com.aol.micro.server.event.metrics;

import org.springframework.stereotype.Component;

import com.aol.micro.server.errors.ErrorCode;
import com.aol.micro.server.events.RequestsBeingExecuted.AddQuery;
import com.aol.micro.server.events.RequestsBeingExecuted.RemoveQuery;
import com.aol.micro.server.events.RequestsBeingExecuted.RequestData;
import com.aol.micro.server.events.SystemData;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Metered;
import com.google.common.eventbus.Subscribe;

@Component
public class QueryMetrics {

    MetricRegistry registry;

    TimerManager queries = new TimerManager(
                                            10_000, 180);

    @Subscribe
    @Metered(name = "requests-started")
    @Counted(name = "request-started-count")
    public void processing(AddQuery data) {
        RequestData rd = data.getData();

        registry.meter(queryStartName(rd))
                .mark();

        queries.start(rd.getCorrelationId(), registry.timer(queryEndName(rd))
                                                     .time());
        registry.counter("queries-active-" + rd.getCorrelationId() + "-count")
                .inc();
    }

    private String queryStartName(RequestData rd) {
        return "query-start-" + rd.getType();
    }

    private String queryEndName(RequestData rd) {
        return "query-end-" + rd.getType();
    }

    @Metered(name = "requests-completed")
    @Counted(name = "request-completed-count")
    @Subscribe
    public void finished(RemoveQuery data) {
        RequestData rd = data.getData();
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
