package com.aol.micro.server.health;

import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aol.cyclops.control.Maybe;

@Component
public class HealthChecker {

    private final long timeThresholdForNormal;

    @Autowired
    public HealthChecker(
            @Value("${health.check.time.threshold.from.error.to.normal:360000}") long timeThresholdForNormalInMillis) {
        this.timeThresholdForNormal = timeThresholdForNormalInMillis;
    }

    HealthStatus checkHealthStatus(final Queue<ErrorEvent> errors, final Queue<ErrorEvent> fatal) {

        Maybe<ErrorEvent> latest = selectLatestError(errors, fatal);
        HealthStatus.State state = state(latest);

        final HealthStatus status = new HealthStatus(
                                                     state, errors, fatal);
        return status;
    }

    private Maybe<ErrorEvent> selectLatestError(Queue<ErrorEvent> errors, Queue<ErrorEvent> fatal) {
        if (errors.size() == 0 && fatal.size() == 0) {
            return Maybe.none();
        }
        if (fatal.size() > 0) {
            return Maybe.just(fatal.peek());
        }
        return Maybe.just(errors.peek());

    }

    private HealthStatus.State state(Maybe<ErrorEvent> errors) {
        return errors.visit(this::handleError, () -> HealthStatus.State.Ok);
    }

    private HealthStatus.State handleError(ErrorEvent event) {
        return event.visit(e -> HealthStatus.State.Fatal, e -> {
            if (System.currentTimeMillis() - event.getTime()
                                                  .getTime() < timeThresholdForNormal) {
                return HealthStatus.State.Errors;
            } else {
                return HealthStatus.State.Ok;
            }
        });
    }
}
