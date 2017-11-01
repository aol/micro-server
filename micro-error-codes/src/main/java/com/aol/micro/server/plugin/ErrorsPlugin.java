package com.aol.micro.server.plugin;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.health.HealthCheck;
import com.aol.micro.server.health.HealthChecker;
import com.aol.micro.server.rest.HealthCheckResource;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class ErrorsPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(HealthCheckResource.class, HealthChecker.class, HealthCheck.class);
    }

}
