package com.aol.micro.server.dist.lock.rest;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.SingletonRestResource;
import com.aol.micro.server.dist.lock.DistributedLockService;
import com.aol.micro.server.dist.lock.LockController;

@Component
@Path("/dist/lock")
public class DistLockResource implements SingletonRestResource {
	
	@Autowired
	LockController lockController;
	
	@Autowired(required=false)
	DistributedLockService lock;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/is/main/process")
	public boolean isMainProcess() {
		return lockController.acquire();		
	}	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/is/main/process/with/{key}")
	public boolean changeToInfo(@PathParam("key") final String key) {
		return Optional.ofNullable(lock).map(service -> service.tryLock(key)).orElse(false);
	}
}
