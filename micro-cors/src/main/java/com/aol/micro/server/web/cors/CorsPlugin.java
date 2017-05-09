package com.aol.micro.server.web.cors;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PSetX;

public class CorsPlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(ConfigureBeans.class);
    }

}
