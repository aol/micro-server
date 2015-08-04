package com.aol.micro.server;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;

import com.aol.cyclops.functions.Memoise;
import com.aol.cyclops.lambda.monads.SequenceM;


public class PluginLoader {

	public final Supplier<List<Plugin>> plugins = Memoise.memoiseSupplier(this::load);

	public final static PluginLoader INSTANCE = new PluginLoader();

	private PluginLoader(){}
	
	
	private List<Plugin> load(){
		 return  SequenceM.fromIterable(ServiceLoader.load(Plugin.class)).toList();
	}
}
