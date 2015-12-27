package com.aol.micro.server.alivehandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;

public class AliveHandlerPlugin implements Plugin {
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(AliveHandlerRest.class));
	}

}
