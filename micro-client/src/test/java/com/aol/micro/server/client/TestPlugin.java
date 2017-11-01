package com.aol.micro.server.client;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.LinkedListX;
import cyclops.collections.mutable.ListX;

import java.util.List;

public class TestPlugin implements Plugin {
	public List<String> providers(){
		return ListX.empty();
	}
}
