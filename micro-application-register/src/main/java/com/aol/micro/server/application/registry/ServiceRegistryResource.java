package com.aol.micro.server.application.registry;

import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import cyclops.stream.ReactiveSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.aol.micro.server.WorkerThreads;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.utility.HashMapBuilder;


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
	public void list(@Suspended AsyncResponse response) {
		ReactiveSeq.of(this)
				.futureOperations(WorkerThreads.ioExecutor.get())
				.forEachX(Long.MAX_VALUE,next -> {
			try{
				cleaner.clean();
				response.resume(finder.find());
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				response.resume(Arrays.asList("failed due to error"));
			}
		});
		
	}

	@POST
	@Path("/schedule")
	@Consumes("application/json")
	@Produces("application/json")
	public void schedule(@Suspended AsyncResponse response) {
		ReactiveSeq.of(this)
				  .futureOperations(WorkerThreads.ioExecutor.get())
				  .forEachX(Long.MAX_VALUE,next -> {
			try{
				job.schedule();
				response.resume(HashMapBuilder.of("status", "success"));
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				response.resume(HashMapBuilder.of("status", "failure"));
			}
		});
		
	}

	@POST
	@Path("/register")
	@Consumes("application/json")
	@Produces("application/json")
	public void register(@Suspended AsyncResponse response,RegisterEntry entry) {
		ReactiveSeq.of(this)
			.futureOperations(WorkerThreads.ioExecutor.get())
			.forEachX(Long.MAX_VALUE,next -> {
			try{
				register.register(entry);
				response.resume(HashMapBuilder.of("status", "complete"));
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				response.resume(HashMapBuilder.of("status", "failure"));
			}
		});
		
	}

}
