package com.aol.micro.server.spring.boot;

import java.util.Map;
import java.util.function.Function;

import javax.ws.rs.core.FeatureContext;

import cyclops.collections.MapXs;
import cyclops.collections.immutable.PMapX;
import cyclops.collections.immutable.PSetX;
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
	public PSetX<Class> springClasses() {
		return PSetX.of(SpringBootJerseyRestApplication.class);
	}

	
	@Override
	public Function<FeatureContext,Map<String,Object>> jacksonFeatureProperties(){
		return context-> PMapX.fromMap(MapXs.of(  CommonProperties.MOXY_JSON_FEATURE_DISABLE + '.'
                + context.getConfiguration().getRuntimeType().name().toLowerCase(),true));
	}
	
	
	

}
