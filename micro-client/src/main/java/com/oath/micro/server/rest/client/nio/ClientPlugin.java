package com.oath.micro.server.rest.client.nio;


import com.oath.micro.server.Plugin;
import cyclops.reactive.collections.mutable.SetX;

import java.util.Set;

/**
 * 
 * @author johnmcclean
 *
 */
public class ClientPlugin implements Plugin{
	
	@Override
	public Set<Class> springClasses() {
		return SetX.of(SpringConfig.class);
	}

	

}
