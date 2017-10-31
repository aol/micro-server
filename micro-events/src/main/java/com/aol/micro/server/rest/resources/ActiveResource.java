package com.aol.micro.server.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import cyclops.reactive.ReactiveSeq;
import org.springframework.beans.factory.annotation.Autowired;


import com.aol.micro.server.WorkerThreads;
import com.aol.micro.server.auto.discovery.CommonRestResource;
import com.aol.micro.server.auto.discovery.SingletonRestResource;
import com.aol.micro.server.events.JobsBeingExecuted;
import com.aol.micro.server.events.RequestTypes;

@Path("/active")
public class ActiveResource<T> implements CommonRestResource, SingletonRestResource {

    private static final Object LOG_LEVEL = null;
    private final RequestTypes<T> activeQueries;
    private final JobsBeingExecuted activeJobs;
    private Long entityIds;

    @Autowired
    public ActiveResource(RequestTypes<T> activeQueries, JobsBeingExecuted activeJobs) {

        this.activeQueries = activeQueries;
        this.activeJobs = activeJobs;
    }

    @GET
    @Produces("application/json")
    @Path("/requests")
    public void activeRequests(@Suspended AsyncResponse asyncResponse, @QueryParam("type") final String type) {

        ReactiveSeq.of((type == null ? "default" : type))
                   .map(typeToUse -> activeQueries.getMap()
                                                  .get(typeToUse)
                                                  .toString())
                   .foldFuture(WorkerThreads.ioExecutor.get(),
                    s->s.forEach(Long.MAX_VALUE,result -> asyncResponse.resume(result)));

    }

    @GET
    @Produces("application/json")
    @Path("/all-requests")
    public void allActiveRequests(@Suspended AsyncResponse asyncResponse) {

        ReactiveSeq.of(activeQueries.toString())
                   .foldFuture(WorkerThreads.ioExecutor.get(),
                   s->s.forEach(Long.MAX_VALUE,result -> asyncResponse.resume(result)));

    }

    @GET
    @Produces("application/json")
    @Path("/jobs")
    public void activeJobs(@Suspended AsyncResponse asyncResponse) {

        ReactiveSeq.of(this.activeJobs)
                   .map(JobsBeingExecuted::toString)
                   .foldFuture(WorkerThreads.ioExecutor.get(),
                   s->s.forEach(Long.MAX_VALUE,str -> asyncResponse.resume(str)));

    }

}
