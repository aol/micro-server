package com.aol.micro.server.jackson;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.rest.jackson.JacksonFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.pcollections.PCollectionsModule;

public class JacksonPlugin implements Plugin {

    @Override
    public PSetX<Class<?>> jaxRsResources() {

        return PSetX.of(JacksonFeature.class);

    }

    @Override
    public PSetX<Class> springClasses() {

        return PSetX.of(CoreJacksonConfigurator.class, JacksonConfigurers.class);

    }

    @Override
    public PSetX<Module> jacksonModules() {
        return PSetX.of(new PCollectionsModule());
    }

}
