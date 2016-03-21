package com.aol.micro.server.reactive;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;


public class ReactivePlugin implements Plugin{

	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(ResponderConfigurer.class);
	}
	
	
	
}
