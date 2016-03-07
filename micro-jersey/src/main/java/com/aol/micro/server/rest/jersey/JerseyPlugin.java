package com.aol.micro.server.rest.jersey;

import java.util.Optional;
import java.util.function.Function;

import javax.servlet.ServletContextListener;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.CommonProperties;

import com.aol.cyclops.data.collections.HashMaps;
import com.aol.cyclops.data.collections.extensions.persistent.PMapX;
import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.rest.RestConfiguration;
import com.aol.micro.server.servers.model.ServerData;


public class JerseyPlugin implements Plugin{
	
	@Override
	public Optional<RestConfiguration> restServletConfiguration(){
		return Optional.of(new ConfigureMainServlet().servletConfig());
	}
	@Override
	public Function<FeatureContext,PMapX<String,Object>> jacksonFeatureProperties(){
		return context->PMapX.fromMap(HashMaps.of(  CommonProperties.MOXY_JSON_FEATURE_DISABLE + '.'
                + context.getConfiguration().getRuntimeType().name().toLowerCase(),true));
	}
	
	@Override
	public  Optional<String> jaxWsRsApplication(){
		return Optional.of(JerseyRestApplication.class.getCanonicalName());
	}
	@Override
	public PSetX<Function<ServerData,ServletContextListener>> servletContextListeners(){
		Function<ServerData,ServletContextListener> f = serverData ->new JerseySpringIntegrationContextListener(serverData);
		return PSetX.of(f);
		
	}
	
}
