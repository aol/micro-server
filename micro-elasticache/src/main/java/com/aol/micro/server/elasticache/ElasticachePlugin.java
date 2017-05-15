package com.aol.micro.server.elasticache;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PSetX;

public class ElasticachePlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
            return PSetX.of(ConfigureElasticache.class);
        }
}