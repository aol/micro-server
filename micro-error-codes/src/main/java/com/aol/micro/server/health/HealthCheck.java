package com.aol.micro.server.health;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.aol.micro.server.errors.ErrorBus;
import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

@Component
@ManagedResource
public class HealthCheck {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    HealthChecker healthCheckHelper = new HealthChecker();

    @Value("${health.check.max.error.list.size:25}")
    int maxSize = 25;

    @Autowired
    EventBus errorBus;

    volatile ImmutableList<ErrorEvent> errors = ImmutableList.<ErrorEvent> of();

    @PostConstruct
    public void register() {
        if (errorBus != null) {
            errorBus.register(this);
            ErrorBus.setErrorBus(errorBus);
        }
    }

    @Subscribe
    public synchronized void onEvent(final ErrorEvent event) {
        ImmutableList<ErrorEvent> newErrors = healthCheckHelper.handleEvent(event, errors, maxSize);
        swapErrors(newErrors);
    }

    public void swapErrors(ImmutableList<ErrorEvent> newErrors) {
        this.errors = newErrors;
    }

    public String checkHealthStatusStringFormat() {
        try {
            final HealthStatus status = checkHealthStatus();
            return JacksonUtil.serializeToJson(status);
        } catch (final Exception e) {
            return "Mapping to JSON failed " + e.getMessage();
        }

    }

    public HealthStatus checkHealthStatus() {
        return healthCheckHelper.checkHealthStatus(errors, false);
    }

    public HealthStatus checkHealthStatusVerbose() {
        return healthCheckHelper.checkHealthStatus(errors, true);
    }

}
