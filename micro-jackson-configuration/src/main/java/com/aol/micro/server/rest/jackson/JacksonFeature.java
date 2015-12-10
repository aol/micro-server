package com.aol.micro.server.rest.jackson;

import java.util.Map;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import com.aol.cyclops.sequence.SequenceM;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;


public class JacksonFeature implements Feature {

    @Override
    public boolean configure(final FeatureContext context) {
        
    	SequenceM.fromStream(PluginLoader.INSTANCE.plugins.get().stream())
		.filter(module -> module.jacksonFeatureProperties()!=null)
		.map(Plugin::jacksonFeatureProperties)
		.map(fn->fn.apply(context))
		.forEach(map -> {
			addAll(map,context);
		});
       
        
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
     		provider.setMapper(JacksonUtil.getMapper());
            context.register(provider, new Class[]{MessageBodyReader.class, MessageBodyWriter.class});
     
        return true;
    }

	private void addAll(Map<String, Object> map, FeatureContext context) {
		for(String key : map.keySet()){
			context.property(key,map.get(key));
		}
		
	}
}
