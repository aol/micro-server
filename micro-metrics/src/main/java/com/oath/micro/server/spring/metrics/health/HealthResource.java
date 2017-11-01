package com.oath.micro.server.spring.metrics.health;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.CommonRestResource;
import com.oath.micro.server.auto.discovery.SingletonRestResource;
import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;

@Path("/health")
@Component
public class HealthResource implements CommonRestResource, SingletonRestResource {

    final HealthCheckRegistry healthChecks;

    public HealthResource(HealthCheckRegistry healthChecks) {
        this.healthChecks = healthChecks;
    }

    @GET
    @Produces("application/json")
    public Map<String, Result> health() {
        return healthChecks.runHealthChecks();

    }
}
