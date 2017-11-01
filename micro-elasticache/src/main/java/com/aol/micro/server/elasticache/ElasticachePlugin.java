package com.aol.micro.server.elasticache;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class ElasticachePlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
            return SetX.of(ConfigureElasticache.class);
        }
}