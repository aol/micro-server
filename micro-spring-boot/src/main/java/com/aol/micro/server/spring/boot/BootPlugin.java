package com.aol.micro.server.spring.boot;

import java.util.Map;
import java.util.function.Function;

import javax.ws.rs.core.FeatureContext;

import cyclops.companion.MapXs;
import cyclops.collections.immutable.PersistentMapX;
import cyclops.collections.immutable.PersistentSetX;
import org.glassfish.jersey.CommonProperties;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.rest.jersey.SpringBootJerseyRestApplication;
import com.aol.micro.server.spring.SpringBuilder;

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
	public PersistentSetX<Class> springClasses() {
		return PersistentSetX.of(SpringBootJerseyRestApplication.class);
	}

	
	@Override
	public Function<FeatureContext,Map<String,Object>> jacksonFeatureProperties(){
		return context-> PersistentMapX.fromMap(MapXs.of(  CommonProperties.MOXY_JSON_FEATURE_DISABLE + '.'
                + context.getConfiguration().getRuntimeType().name().toLowerCase(),true));
	}
	
	
	

}
