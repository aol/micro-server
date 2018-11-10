package com.oath.micro.server.dist.lock.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cyclops.reactive.collections.mutable.ListX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.SingletonRestResource;
import com.oath.micro.server.dist.lock.DistributedLockManager;

@Component
@Path("/lock-owner")
public class DistLockResource implements SingletonRestResource {

    private final Map<String, DistributedLockManager> lockController;

    @Autowired(required = false)
    public DistLockResource(List<DistributedLockManager> lockController) {
        this.lockController = ListX.fromIterable(lockController)
                                   .toMap(lc -> lc.getKey(), lc -> lc);
    }

    public DistLockResource() {
        lockController = new HashMap<>();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{lockName}")
    public boolean ownLock(@PathParam("lockName") final String lockName) {
        if (!lockController.containsKey(lockName))
            return false;
        return lockController.get(lockName)
                             .isMainProcess();
    }

}
