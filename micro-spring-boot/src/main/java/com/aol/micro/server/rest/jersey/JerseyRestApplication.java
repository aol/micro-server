package com.aol.micro.server.rest.jersey;

import java.util.List;
import java.util.Map;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.module.JaxRsProvider;
import com.aol.micro.server.module.Module;
import com.aol.micro.server.module.ModuleDataExtractor;

public class JerseyRestApplication extends ResourceConfig {

	

	@Autowired(required=false)
	public JerseyRestApplication(ApplicationContext context){
		this(context, ()->"");
	}
	
	@Autowired(required=false)
	public JerseyRestApplication(ApplicationContext context,Module module){
		ModuleDataExtractor extractor = new ModuleDataExtractor(module);
		
		List allResources = extractor.getRestResources(context);
		
		Map<String, Object> serverProperties = module.getServerProperties();
		if (allResources != null) {
			for (Object next : allResources) {
				if(isSingleton(next))
					register(next);
				else
					register(next.getClass());
			}
		}
		
		
		if (serverProperties.isEmpty()) {
			property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	        //http://stackoverflow.com/questions/25755773/bean-validation-400-errors-are-returning-default-error-page-html-instead-of-re
	        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");
		} else {
			for (Map.Entry<String, Object> entry : serverProperties.entrySet()) {
				property(entry.getKey(), entry.getValue());
			}
		}
		
		module.getDefaultJaxRsPackages().stream().forEach( e -> packages(e));
		module.getDefaultResources().stream().forEach( e -> register(e));
		
		
		module.getResourceConfigManager().accept(new JaxRsProvider<>(this));
	}
	
	


	private boolean isSingleton(Object next) {
		if(next instanceof RestResource)
			return ((RestResource)next).isSingleton();
		Rest rest = next.getClass().getAnnotation(Rest.class);
		if(rest == null)
			return false;
		return rest.isSingleton();
	}

	

}
