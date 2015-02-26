package com.aol.micro.server.rest.resources;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.CommonRestResource;
import com.aol.micro.server.auto.discovery.SingletonRestResource;
import com.aol.micro.server.events.JobsBeingExecuted;
import com.aol.micro.server.events.RequestsBeingExecuted;
import com.google.common.collect.Maps;



@Path("/active")
public class ActiveResource implements CommonRestResource, SingletonRestResource {

	private final Map<String,RequestsBeingExecuted> activeQueries;
	private final JobsBeingExecuted activeJobs;
	
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
	public String activeRequests(@QueryParam("type") final String  type) {
		final String typeToUse = (type == null ? "default" : type);
		return activeQueries.get(typeToUse).toString();
	}
	@GET
	@Produces("application/json")
	@Path("/jobs")
	public String activeJobs() {
		
		return activeJobs.toString();
	}
	
	
}
