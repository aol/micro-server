package com.aol.micro.server.rest.resources;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.CommonRestResource;
import com.aol.micro.server.auto.discovery.Pipes;
import com.aol.micro.server.auto.discovery.SingletonRestResource;
import com.aol.micro.server.events.JobsBeingExecuted;
import com.aol.micro.server.events.RequestsBeingExecuted;
import com.aol.simple.react.async.QueueFactories;
import com.aol.simple.react.stream.traits.LazyFutureStream;
import com.aol.simple.react.stream.traits.SimpleReactStream;
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

	private void updateLogLevel(String level){
		
	}
	@GET
	@Produces("application/json")
	@Path("/requests")
	public void activeRequests(@Suspended AsyncResponse asyncResponse,@QueryParam("type") final String  type) {
		
		this.sync(lr-> lr.of((type == null ? "default" : type))
							.map(typeToUse->activeQueries.get(typeToUse).toString())
							.peek(result->asyncResponse.resume(result)))
							.run();
	}
	
	
	
	@GET
	@Produces("application/json")
	@Path("/jobs")
	public void activeJobs(@Suspended AsyncResponse asyncResponse) {
		
		this.sync(lr->lr.of(this.activeJobs)
								  .then(JobsBeingExecuted::toString)
								  .then(str->asyncResponse.resume(str)))
								  .run();
		
	}
	
	
	
	
}
