package com.aol.micro.server.rest.client.nio;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PSetX;

/**
 * 
 * @author johnmcclean
 *
 */
public class ClientPlugin implements Plugin{
	
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(SpringConfig.class);
	}

	

}
