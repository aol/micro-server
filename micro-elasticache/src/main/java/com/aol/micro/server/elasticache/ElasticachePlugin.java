package com.aol.micro.server.elasticache;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;

public class ElasticachePlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
            return PSetX.of(ConfigureElasticache.class);
        }
}