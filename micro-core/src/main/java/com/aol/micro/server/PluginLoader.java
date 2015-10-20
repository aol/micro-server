package com.aol.micro.server;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.aol.cyclops.functions.caching.Memoize;
import com.aol.cyclops.sequence.SequenceM;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class PluginLoader {

	public final static PluginLoader INSTANCE = new PluginLoader();
	
	public final Supplier<List<Plugin>> plugins = Memoize.memoizeSupplier(this::load);

	private List<Plugin> load(){
		 return  SequenceM.fromIterable(ServiceLoader.load(Plugin.class)).toList();
	}
}
