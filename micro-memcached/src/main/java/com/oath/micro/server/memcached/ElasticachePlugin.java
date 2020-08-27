package com.oath.micro.server.memcached;

import com.oath.micro.server.Plugin;
import cyclops.reactive.collections.mutable.SetX;

import java.util.Set;

public class ElasticachePlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
            return SetX.of(ElasticacheConfig.class, DistributedCacheFactory.class);
        }
}
