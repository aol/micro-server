package com.aol.micro.server.spring.boot;

import java.util.function.Function;

import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.CommonProperties;

import com.aol.cyclops.data.collections.HashMaps;
import com.aol.cyclops.data.collections.extensions.persistent.PMapX;
import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.rest.jersey.JerseyRestApplication;
import com.aol.micro.server.spring.SpringBuilder;
import com.aol.micro.server.spring.boot.web.DelegatingContextListener;

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
		return PSetX.of(JerseyRestApplication.class,DelegatingContextListener.class);
	}

	
	@Override
	public Function<FeatureContext,PMapX<String,Object>> jacksonFeatureProperties(){
		return context->PMapX.fromMap(HashMaps.of(  CommonProperties.MOXY_JSON_FEATURE_DISABLE + '.'
                + context.getConfiguration().getRuntimeType().name().toLowerCase(),true));
	}
	
	
	

}
