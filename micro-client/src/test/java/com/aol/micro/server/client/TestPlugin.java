package com.aol.micro.server.client;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PStackX;

public class TestPlugin implements Plugin {
	public PStackX<String> providers(){
		return PStackX.empty();
	}
}
