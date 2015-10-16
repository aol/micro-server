package com.aol.micro.server;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.aol.cyclops.functions.Memoise;
import com.aol.cyclops.lambda.monads.SequenceM;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class PluginLoader {

	public final static PluginLoader INSTANCE = new PluginLoader();
	
	public final Supplier<List<Plugin>> plugins = Memoise.memoiseSupplier(this::load);

	private List<Plugin> load(){
		 return  SequenceM.fromIterable(ServiceLoader.load(Plugin.class)).toList();
	}
}
