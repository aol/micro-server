package com.aol.micro.server.application.registry;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import cyclops.collections.mutable.ListX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.aol.micro.server.StatsSupplier;

@Component
public class RegistryStatsChecker {

    private final ListX<StatsSupplier> status;
    private final boolean active;

    public RegistryStatsChecker() {
        status = ListX.empty();
        this.active = false;
    }

    @Autowired(required = false)
    public RegistryStatsChecker(final List<StatsSupplier> status,
            @Value("${service.registry.stats.active:true}") boolean active) {
        this.status = ListX.fromIterable(status);
        this.active = active;
    }

    public List<Map<String, Map<String, String>>> stats() {
        if (!active)
            return null;
        return status.map(StatsSupplier::get)
                     .filter(Objects::nonNull);
    }
}
