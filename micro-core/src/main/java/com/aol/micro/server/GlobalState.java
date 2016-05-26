package com.aol.micro.server;

import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.aol.micro.server.module.Module;

import lombok.Getter;
import lombok.Setter;

public class GlobalState {

	public static final GlobalState state = new GlobalState();
	
	private GlobalState(){ }
	
	@Getter @Setter
	private volatile ListX<Module> modules;
}
