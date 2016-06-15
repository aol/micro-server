package com.aol.micro.server.dist.lock.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.SingletonRestResource;
import com.aol.micro.server.dist.lock.LockController;

@Component
@Path("/dist/lock")
public class DistLockResource implements SingletonRestResource {
	
	private final LockController lockController;
	
	@Autowired
	public DistLockResource(LockController lockController) {
		this.lockController = lockController;				
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/own/lock/{lockName}")
	public boolean ownLock(@PathParam("lockName") final String lockName) {
		return lockController.acquire(lockName);		
	}		
}
