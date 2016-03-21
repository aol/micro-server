package com.aol.micro.server.rest.resources;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.micro.server.WorkerThreads;
import com.aol.micro.server.auto.discovery.CommonRestResource;
import com.aol.micro.server.auto.discovery.SingletonRestResource;
import com.aol.micro.server.events.JobsBeingExecuted;
import com.aol.micro.server.events.RequestsBeingExecuted;
import com.google.common.collect.Maps;



@Path("/active")
public class ActiveResource implements CommonRestResource, SingletonRestResource {

	private static final Object LOG_LEVEL = null;
	private final Map<String,RequestsBeingExecuted> activeQueries;
	private final JobsBeingExecuted activeJobs;
	private Long entityIds;
	
	@Autowired
	public ActiveResource(List<RequestsBeingExecuted> activeQueries,JobsBeingExecuted activeJobs) {
		Map<String,RequestsBeingExecuted> map = Maps.newHashMap();
		for(RequestsBeingExecuted next:  activeQueries){
			map.put(next.getType(),next);
		}
		this.activeQueries = map;
		this.activeJobs = activeJobs;
	}

	
	@GET
	@Produces("application/json")
	@Path("/requests")
	public void activeRequests(@Suspended AsyncResponse asyncResponse,@QueryParam("type") final String  type) {
		
		ReactiveSeq.of((type == null ? "default" : type))
							.map(typeToUse->activeQueries.get(typeToUse).toString())
							.futureOperations(WorkerThreads.ioExecutor.get())
							.forEach(result->asyncResponse.resume(result));
							
	}
	
	
	
	@GET
	@Produces("application/json")
	@Path("/jobs")
	public void activeJobs(@Suspended AsyncResponse asyncResponse) {
		
		ReactiveSeq.of(this.activeJobs)
								  .map(JobsBeingExecuted::toString)
								  .futureOperations(WorkerThreads.ioExecutor.get())
								  .forEach(str->asyncResponse.resume(str));
								  
		
	}
	
	
	
	
}
