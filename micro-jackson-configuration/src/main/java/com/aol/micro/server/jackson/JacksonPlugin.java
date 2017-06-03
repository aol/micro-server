package com.aol.micro.server.jackson;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.rest.jackson.JacksonFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.pcollections.PCollectionsModule;
import cyclops.collections.immutable.PersistentSetX;

public class JacksonPlugin implements Plugin {

    @Override
    public PersistentSetX<Class<?>> jaxRsResources() {

        return PersistentSetX.of(JacksonFeature.class);

    }

    @Override
    public PersistentSetX<Class> springClasses() {

        return PersistentSetX.of(CoreJacksonConfigurator.class, JacksonConfigurers.class);

    }

    @Override
    public PersistentSetX<Module> jacksonModules() {
        return PersistentSetX.of(new PCollectionsModule());
    }

}
