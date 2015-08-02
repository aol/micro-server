package com.aol.micro.server.rest.client.nio;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.ServletContextListener;

import com.aol.micro.server.FunctionalModule;
import com.aol.micro.server.servers.model.ServerData;

/**
 * 
 * @author johnmcclean
 *
 */
public class ClientFunctionalModule implements FunctionalModule{
	
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(SpringConfig.class));
	}

	

}
