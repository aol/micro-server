package com.aol.micro.server.rest.client.nio;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;

/**
 * 
 * @author johnmcclean
 *
 */
public class ClientPlugin implements Plugin{
	
	@Override
	public PersistentSetX<Class> springClasses() {
		return PersistentSetX.of(SpringConfig.class);
	}

	

}
