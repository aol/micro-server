package com.aol.micro.server.client;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.LinkedListX;

public class TestPlugin implements Plugin {
	public LinkedListX<String> providers(){
		return LinkedListX.empty();
	}
}
