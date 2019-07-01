package com.oath.micro.server.boot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.ws.rs.core.FeatureContext;

import cyclops.reactive.collections.mutable.MapX;
import cyclops.reactive.collections.mutable.SetX;
import cyclops.reactive.companion.MapXs;
import org.glassfish.jersey.CommonProperties;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.spring.SpringBuilder;

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
        return SetX.empty();
    }
	
	@Override
	public Function<FeatureContext,Map<String,Object>> jacksonFeatureProperties(){
		return context-> new HashMap<>();
	}

}
