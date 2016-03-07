package com.aol.micro.server;

import java.util.ServiceLoader;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.aol.cyclops.control.FluentFunctions;
import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.data.collections.extensions.standard.ListX;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class PluginLoader {

	public final static PluginLoader INSTANCE = new PluginLoader();
	
	public final Supplier<ListX<Plugin>> plugins = FluentFunctions.of(this::load)
																 .memoize();

	private ListX<Plugin> load(){
		 return  ReactiveSeq.fromIterable(ServiceLoader.load(Plugin.class)).toListX();
	}
}
