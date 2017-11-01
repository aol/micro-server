package com.aol.micro.server.web.cors;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class CorsPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(ConfigureBeans.class);
    }

}
