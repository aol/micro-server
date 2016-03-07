package com.aol.micro.server.rest.client.nio;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;

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
