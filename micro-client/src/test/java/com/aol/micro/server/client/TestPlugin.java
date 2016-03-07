package com.aol.micro.server.client;

import com.aol.cyclops.data.collections.extensions.persistent.PStackX;
import com.aol.micro.server.Plugin;

public class TestPlugin implements Plugin {
	public PStackX<String> providers(){
		return PStackX.empty();
	}
}
