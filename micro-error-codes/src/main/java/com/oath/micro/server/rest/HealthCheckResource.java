package com.oath.micro.server.rest;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.CommonRestResource;
import com.oath.micro.server.auto.discovery.SingletonRestResource;
import com.oath.micro.server.health.HealthCheck;
import com.oath.micro.server.utility.HashMapBuilder;

@Path("/system-errors")
@Component
public class HealthCheckResource implements CommonRestResource, SingletonRestResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HealthCheck healthCheck;
    private final boolean showErrors;

    @Autowired
    public HealthCheckResource(HealthCheck healthCheck, @Value("${health.check.show.errors:true}") boolean showErrors) {
        this.healthCheck = healthCheck;
        this.showErrors = showErrors;

    }

    @POST
    @Path("/max-errors/{maxErrors}")
    @Produces("application/json")
    public Map<String, Integer> setMaxErrors(@PathParam("maxErrors") int maxErrors) {
        healthCheck.setMaxSize(maxErrors);
        return getMaxErrors();
    }

    @GET
    @Path("/max-errors")
    @Produces("application/json")
    public Map<String, Integer> getMaxErrors() {

        return HashMapBuilder.of("maxErrors", healthCheck.getMaxSize());
    }

    @GET
    @Path("/status")
    @Produces("text/plain")
    public String ping() {
        return healthCheck.checkHealthStatus()
                          .getGeneralProcessing()
                          .name();
    }

    @GET
    @Path("/errors")
    @Produces("application/json")
    public Response errors() {
        if (!showErrors)
            Response.status(Status.UNAUTHORIZED)
                    .build();
        return Response.ok(healthCheck.checkHealthStatus())
                       .build();
    }

}