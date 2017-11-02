package com.oath.micro.server.elasticache;

import com.oath.micro.server.Plugin;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class ElasticachePlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
            return SetX.of(ConfigureElasticache.class);
        }
}