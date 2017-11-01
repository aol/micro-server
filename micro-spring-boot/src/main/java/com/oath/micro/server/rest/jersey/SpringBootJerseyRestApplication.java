package com.oath.micro.server.rest.jersey;

import java.util.Map;

import cyclops.collections.immutable.LinkedListX;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.oath.micro.server.GlobalState;
import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.auto.discovery.RestResource;
import com.oath.micro.server.module.JaxRsProvider;
import com.oath.micro.server.module.Module;
import com.oath.micro.server.module.ModuleDataExtractor;

public class SpringBootJerseyRestApplication extends ResourceConfig {

	@Autowired(required=false)
	public SpringBootJerseyRestApplication(ApplicationContext context){
		this(context, GlobalState.state.getModules().firstValue(null));
	}
	
	@Autowired(required=false)
	public SpringBootJerseyRestApplication(ApplicationContext context,Module module){
		ModuleDataExtractor extractor = new ModuleDataExtractor(module);
		
		LinkedListX allResources = extractor.getRestResources(context);
		
		System.out.println("Resources " + allResources);
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
		
		context.getBeansOfType(AbstractBinder.class).forEach((n,e)->register(e));
		
		
		module.getDefaultJaxRsPackages().stream().forEach( e -> packages(e));
		module.getDefaultResources().stream().forEach( e -> register(e));
		
		
		module.getResourceConfigManager().accept(new JaxRsProvider<>(this));
	}
	

	private boolean isSingleton(Object next) {
		if(next instanceof RestResource)
			return ((RestResource)next).isSingleton();
		Rest rest = next.getClass().getAnnotation(Rest.class);
		if(rest == null)
			return !(next instanceof Class);
		return rest.isSingleton();
	}


	

}
