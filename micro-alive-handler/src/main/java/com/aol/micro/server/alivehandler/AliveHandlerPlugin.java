package com.aol.micro.server.alivehandler;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;

public class AliveHandlerPlugin implements Plugin {
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(AliveHandlerRest.class);
	}

}
