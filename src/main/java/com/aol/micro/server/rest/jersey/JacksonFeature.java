package com.aol.micro.server.rest.jersey;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.glassfish.jersey.CommonProperties;

import com.aol.micro.server.rest.JacksonUtil;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;


public class JacksonFeature implements Feature {

    @Override
    public boolean configure(final FeatureContext context) {
        final String disableMoxy = CommonProperties.MOXY_JSON_FEATURE_DISABLE + '.'
                + context.getConfiguration().getRuntimeType().name().toLowerCase();
        context.property(disableMoxy, true);
        
        context.register(JacksonJaxbJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
     		provider.setMapper(JacksonUtil.getMapper());
            context.register(provider);
     
        return true;
    }
}
