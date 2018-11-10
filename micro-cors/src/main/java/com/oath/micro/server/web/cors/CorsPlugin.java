package com.oath.micro.server.web.cors;


import com.oath.micro.server.Plugin;
import cyclops.reactive.collections.mutable.SetX;

import java.util.Set;

public class CorsPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(ConfigureBeans.class);
    }

}
