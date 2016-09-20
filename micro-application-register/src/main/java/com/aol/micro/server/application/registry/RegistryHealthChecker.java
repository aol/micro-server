package com.aol.micro.server.application.registry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.cyclops.Monoid;
import com.aol.cyclops.Semigroups;
import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.aol.micro.server.HealthStatusChecker;

@Component
public class RegistryHealthChecker {

    private final ListX<HealthStatusChecker> status;

    public RegistryHealthChecker() {
        status = ListX.empty();
    }

    @Autowired(required = false)
    public RegistryHealthChecker(final List<HealthStatusChecker> status) {
        this.status = ListX.fromIterable(status);
    }

    public boolean isOk() {

        return status.map(hs -> hs.isOk())
                     .reduce(Monoid.of(true, Semigroups.booleanConjunction));
    }
}
