package com.aol.micro.server.web.cors;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;

public class CorsPlugin implements Plugin {

    @Override
    public PersistentSetX<Class> springClasses() {
        return PersistentSetX.of(ConfigureBeans.class);
    }

}
