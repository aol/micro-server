package com.aol.micro.server.rest.client.nio;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

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
