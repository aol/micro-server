package com.aol.micro.server.ip.tracker;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;




public class IPTrackerPlugin implements Plugin{

	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(BeanConfiguration.class);
		
	}
	

	

}
