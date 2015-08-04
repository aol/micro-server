package com.aol.micro.server.rest.jersey;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.ServletContextListener;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.CommonProperties;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.rest.RestConfiguration;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.utility.HashMapBuilder;


public class JerseyPlugin implements Plugin{
	
	@Override
	public Optional<RestConfiguration> restServletConfiguration(){
		return Optional.of(new ConfigureMainServlet().servletConfig());
	}
	@Override
	public Function<FeatureContext,Map<String,Object>> jacksonFeatureProperties(){
		return context->HashMapBuilder.of(  CommonProperties.MOXY_JSON_FEATURE_DISABLE + '.'
                + context.getConfiguration().getRuntimeType().name().toLowerCase(),true);
	}
	
	@Override
	public  Optional<String> jaxWsRsApplication(){
		return Optional.of(JerseyRestApplication.class.getCanonicalName());
	}
	@Override
	public Set<Function<ServerData,ServletContextListener>> servletContextListeners(){
		Function<ServerData,ServletContextListener> f = serverData ->new JerseySpringIntegrationContextListener(serverData);
		Set<Function<ServerData,ServletContextListener>> set = new HashSet<>();
		set.add(f);
		return set;
	}
	
}
