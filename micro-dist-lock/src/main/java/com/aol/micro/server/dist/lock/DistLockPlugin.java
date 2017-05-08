package com.aol.micro.server.dist.lock;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.dist.lock.rest.DistLockResource;
import cyclops.collections.immutable.PSetX;

/**
 * 
 * @author Ke Wang
 *
 */
public class DistLockPlugin implements Plugin {
    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(DistLockResource.class);
    }

}
