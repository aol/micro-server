package com.aol.micro.server.spring.boot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.CommonProperties;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.rest.jersey.JerseyRestApplication;
import com.aol.micro.server.spring.SpringBuilder;
import com.aol.micro.server.spring.boot.web.DelegatingContextListener;
import com.aol.micro.server.utility.HashMapBuilder;

/**
 * 
 * @author johnmcclean
 *
 */
public class BootPlugin implements Plugin{
	
	
	/**
	 * @return Engine for building Spring Context
	 */
	public SpringBuilder springBuilder(){
		return new BootFrontEndApplicationConfigurator();
	}

	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(JerseyRestApplication.class,DelegatingContextListener.class));
	}

	
	@Override
	public Function<FeatureContext,Map<String,Object>> jacksonFeatureProperties(){
		return context->HashMapBuilder.of(  CommonProperties.MOXY_JSON_FEATURE_DISABLE + '.'
                + context.getConfiguration().getRuntimeType().name().toLowerCase(),true);
	}
	
	
	

}
