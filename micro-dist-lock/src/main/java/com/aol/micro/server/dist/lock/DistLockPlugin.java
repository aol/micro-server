package com.aol.micro.server.dist.lock;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.dist.lock.rest.DistLockResource;
import cyclops.collections.immutable.PersistentSetX;

/**
 * 
 * @author Ke Wang
 *
 */
public class DistLockPlugin implements Plugin {
    @Override
    public PersistentSetX<Class> springClasses() {
        return PersistentSetX.of(DistLockResource.class);
    }

}
