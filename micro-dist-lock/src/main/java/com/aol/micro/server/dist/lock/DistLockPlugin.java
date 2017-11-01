package com.aol.micro.server.dist.lock;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.dist.lock.rest.DistLockResource;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

/**
 * 
 * @author Ke Wang
 *
 */
public class DistLockPlugin implements Plugin {
    @Override
    public Set<Class> springClasses() {
        return SetX.of(DistLockResource.class);
    }

}
