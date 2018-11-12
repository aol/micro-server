package com.oath.micro.server.plugin;

import com.oath.micro.server.Plugin;
import com.oath.micro.server.health.HealthCheck;
import com.oath.micro.server.health.HealthChecker;
import com.oath.micro.server.rest.HealthCheckResource;
import cyclops.reactive.collections.mutable.SetX;

import java.util.Set;

public class ErrorsPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(HealthCheckResource.class, HealthChecker.class, HealthCheck.class);
    }

}
