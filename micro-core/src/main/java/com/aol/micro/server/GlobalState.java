package com.aol.micro.server;


import com.aol.micro.server.module.Module;

import cyclops.collections.mutable.ListX;
import lombok.Getter;
import lombok.Setter;

public class GlobalState {

	public static final GlobalState state = new GlobalState();
	
	private GlobalState(){ }
	
	@Getter @Setter
	private volatile ListX<Module> modules;
}
