package com.aol.micro.server.jackson;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.rest.jackson.JacksonFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.pcollections.PCollectionsModule;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class JacksonPlugin implements Plugin {

    @Override
    public Set<Class<?>> jaxRsResources() {

        return SetX.of(JacksonFeature.class);

    }

    @Override
    public Set<Class> springClasses() {

        return SetX.of(CoreJacksonConfigurator.class, JacksonConfigurers.class);

    }

    @Override
    public Set<Module> jacksonModules() {
        return SetX.of(new PCollectionsModule());
    }

}
