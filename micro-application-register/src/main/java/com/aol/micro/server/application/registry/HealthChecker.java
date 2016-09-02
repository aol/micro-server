package com.aol.micro.server.application.registry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.cyclops.Monoid;
import com.aol.cyclops.Semigroups;
import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.aol.micro.server.HealthStatus;

@Component
public class HealthChecker {

    private final ListX<HealthStatus> status;

    public HealthChecker() {
        status = ListX.empty();
    }

    @Autowired(required = false)
    public HealthChecker(final List<HealthStatus> status) {
        this.status = ListX.fromIterable(status);
    }

    public boolean isOk() {

        return status.map(hs -> hs.isOk())
                     .reduce(Monoid.of(true, Semigroups.booleanConjunction));
    }
}
