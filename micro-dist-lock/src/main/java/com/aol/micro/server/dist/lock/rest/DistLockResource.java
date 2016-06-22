package com.aol.micro.server.dist.lock.rest;

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
	
	private final LockController lockController;
	private final DistributedLockService lock;
	
	@Autowired
	public DistLockResource(LockController lockController, DistributedLockService lock) {
		this.lockController = lockController;	
		this.lock = lock;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/own/lock/{lockName}")
	public boolean ownLock(@PathParam("lockName") final String lockName) {
		return lockController.acquire(lockName);		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/own/lock/for/key/{lockKey}")
	public boolean ownLockForKey(@PathParam("lockKey") final String lockKey) {
		return lock.tryLock(lockKey);		
	}		
}
