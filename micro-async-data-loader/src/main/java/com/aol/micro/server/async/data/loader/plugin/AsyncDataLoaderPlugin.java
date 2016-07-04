package com.aol.micro.server.async.data.loader.plugin;

import java.util.Set;

import com.aol.cyclops.data.collections.extensions.standard.SetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.async.data.loader.ConfigureScheduling;

public class AsyncDataLoaderPlugin implements Plugin {

	@Override
	public Set<Class> springClasses() {
		return SetX.of(ConfigureScheduling.class);
	}

}
