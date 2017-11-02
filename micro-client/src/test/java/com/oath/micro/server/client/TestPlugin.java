package com.oath.micro.server.client;


import com.oath.micro.server.Plugin;
import cyclops.collections.mutable.ListX;

import java.util.List;

public class TestPlugin implements Plugin {
	public List<String> providers(){
		return ListX.empty();
	}
}
