package com.oath.micro.server;


import com.oath.micro.server.module.Module;

import cyclops.collections.mutable.ListX;
import lombok.Getter;
import lombok.Setter;

public class GlobalState {

	public static final GlobalState state = new GlobalState();
	
	private GlobalState(){ }
	
	@Getter @Setter
	private volatile ListX<Module> modules;
}
