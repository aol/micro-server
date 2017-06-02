package com.aol.micro.server;

import java.util.ServiceLoader;
import java.util.function.Supplier;

import cyclops.collections.mutable.ListX;
import cyclops.function.FluentFunctions;
import cyclops.stream.ReactiveSeq;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;



@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class PluginLoader {

	public final static PluginLoader INSTANCE = new PluginLoader();
	
	public final Supplier<ListX<Plugin>> plugins = FluentFunctions.of(this::load)
																 .memoize();

	private ListX<Plugin> load(){
		 return  ReactiveSeq.fromIterable(ServiceLoader.load(Plugin.class)).toListX();
	}
}
