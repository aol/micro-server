package com.aol.micro.server.application.registry;

import java.util.Arrays;
import java.util.Optional;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import cyclops.reactive.ReactiveSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.aol.micro.server.WorkerThreads;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.utility.HashMapBuilder;

import static javax.ws.rs.core.Response.Status.*;


@Rest
@Path("/service-registry")
public class ServiceRegistryResource{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final Cleaner cleaner;
	private final Finder finder;
	private final Register register;
	private final Job job;

	@Autowired
	public ServiceRegistryResource(Cleaner cleaner, Finder finder, Register register, Job job) {

		this.cleaner = cleaner;
		this.finder = finder;
		this.register = register;
		this.job = job;
	}

	@GET
	@Path("/list")
	@Produces("application/json")
	public void list(@Context UriInfo uriInfo, @Suspended AsyncResponse response) {
		ReactiveSeq.of(this).foldFuture(WorkerThreads.ioExecutor.get(),
				s->s.forEach(Long.MAX_VALUE,next -> {
			try{
				cleaner.clean();
				response.resume(finder.find(UriInfoParser.toRegisterEntry(uriInfo)));
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				response.resume(Arrays.asList("Bad Request: " + e.getMessage()));
			}
		}));
	}

	@POST
	@Path("/schedule")
	@Consumes("application/json")
	@Produces("application/json")
	public void schedule(@Suspended AsyncResponse response) {
		ReactiveSeq.of(this).foldFuture(WorkerThreads.ioExecutor.get(),s->
				  s.forEach(Long.MAX_VALUE,next -> {
			try{
				job.schedule();
				response.resume(HashMapBuilder.of("status", "success"));
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				response.resume(HashMapBuilder.of("status", "failure"));
			}
		}));
		
	}

	@POST
	@Path("/register")
	@Consumes("application/json")
	@Produces("application/json")
	public void register(@Suspended AsyncResponse response,RegisterEntry entry) {
		ReactiveSeq.of(this).foldFuture(WorkerThreads.ioExecutor.get(),
			s->s.forEach(Long.MAX_VALUE,next -> {
			try{
				register.register(entry);
				response.resume(HashMapBuilder.of("status", "complete"));
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				response.resume(HashMapBuilder.of("status", "failure"));
			}
		}));
		
	}

}
