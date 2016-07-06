package com.aol.micro.server.health;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

@Component
public class HealthChecker {

    @Value("${health.check.time.threshold.from.error.to.normal:360000}")
    Long timeThresholdForNormal = 360000l; // 60 mins

    HealthStatus checkHealthStatus(final List<ErrorEvent> errors, final boolean verbose) {

        final HealthStatus status = new HealthStatus();

        status.recentErrors = errors;

        assignGeneralProcessingToStatus(status, errors);

        return status;
    }

    <T> ImmutableList<T> handleEvent(final T event, final ImmutableList<T> list, final int max) {
        return list.size() < max ? ImmutableList.<T> builder()
                                                .addAll(list)
                                                .add(event)
                                                .build()
                : ImmutableList.<T> builder()
                               .addAll(list.subList(1, list.size()))
                               .add(event)
                               .build();
    }

    private void assignGeneralProcessingToStatus(HealthStatus status, List<ErrorEvent> errors) {
        if (errors.size() > 0) {

            final ErrorEvent event = errors.get(errors.size() - 1);

            if (System.currentTimeMillis() - event.time.getTime() < timeThresholdForNormal) {
                status.generalProcessing = HealthStatus.State.Errors;
            } else {
                status.generalProcessing = HealthStatus.State.Ok;
            }
        } else {
            status.generalProcessing = HealthStatus.State.Ok;
        }
    }

}
