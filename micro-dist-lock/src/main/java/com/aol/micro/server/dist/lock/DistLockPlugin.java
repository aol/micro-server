package com.aol.micro.server.dist.lock;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.dist.lock.rest.DistLockResource;

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
