package com.aol.micro.server.health;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aol.micro.server.errors.ErrorBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import lombok.Getter;

@Component
public class HealthCheck {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final HealthChecker healthCheckHelper;

    @Getter
    private volatile int maxSize;

    private final int hardMax;
    private final EventBus errorBus;

    @Autowired
    public HealthCheck(HealthChecker healthChecker, @Value("${health.check.error.list.size:25}") int maxSize,
            @Value("${health.check.max.error.list.size:2500}") int hardMax, EventBus errorBus) {
        this.healthCheckHelper = healthChecker;
        this.maxSize = maxSize;
        this.hardMax = hardMax;
        this.errorBus = errorBus;
    }

    private final ConcurrentLinkedQueue<ErrorEvent> errors = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<ErrorEvent> fatalErrors = new ConcurrentLinkedQueue<>();

    @PostConstruct
    public void register() {
        errorBus.register(this);
        ErrorBus.setErrorBus(errorBus);
    }

    public void setMaxSize(int maxSize) {
        if (maxSize <= hardMax)
            this.maxSize = maxSize;
    }

    private Void handle(ErrorEvent e, ConcurrentLinkedQueue<ErrorEvent> queue) {
        if (queue.size() > maxSize)
            queue.poll();
        queue.offer(e);
        return null;
    }

    @Subscribe
    public void onEvent(final ErrorEvent event) {
        event.visit(e -> handle(e, fatalErrors), e -> handle(e, errors));

    }

    public HealthStatus checkHealthStatus() {
        return healthCheckHelper.checkHealthStatus(errors, this.fatalErrors);
    }

}
