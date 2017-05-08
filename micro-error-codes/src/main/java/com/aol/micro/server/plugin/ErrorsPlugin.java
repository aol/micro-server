package com.aol.micro.server.plugin;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.health.HealthCheck;
import com.aol.micro.server.health.HealthChecker;
import com.aol.micro.server.rest.HealthCheckResource;
import cyclops.collections.immutable.PSetX;

public class ErrorsPlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(HealthCheckResource.class, HealthChecker.class, HealthCheck.class);
    }

}
