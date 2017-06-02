package com.aol.micro.server.elasticache;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;

public class ElasticachePlugin implements Plugin {

    @Override
    public PersistentSetX<Class> springClasses() {
            return PersistentSetX.of(ConfigureElasticache.class);
        }
}