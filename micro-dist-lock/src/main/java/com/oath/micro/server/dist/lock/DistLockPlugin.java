package com.oath.micro.server.dist.lock;

import com.oath.micro.server.Plugin;
import com.oath.micro.server.dist.lock.rest.DistLockResource;
import cyclops.reactive.collections.mutable.SetX;

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
