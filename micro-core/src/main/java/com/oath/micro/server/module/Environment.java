package com.oath.micro.server.module;

import java.net.InetAddress;
import java.util.Collection;
import cyclops.data.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import com.oath.cyclops.types.persistent.PersistentMap;
import cyclops.control.Try;
import cyclops.data.tuple.Tuple;
import cyclops.reactive.ReactiveSeq;

import static cyclops.data.tuple.Tuple.tuple;


public class Environment {

	private volatile PersistentMap<String, ModuleBean> modulePort;
	private final Properties properties;
	private volatile int nextPort = 8080;

	public Environment(Properties propertyFactory, Collection<ModuleBean> modules) {

		modulePort = HashMap.fromStream(modules.stream().map(m-> tuple(m.getModule().getContext(),m)));
		this.properties = propertyFactory;
	}

	public Environment(Properties propertyFactory) {
		modulePort = HashMap.empty();
		this.properties = propertyFactory;

	}

	public ModuleBean getModuleBean(Module module) {
		return modulePort.getOrElse(module.getContext(),null);
	}

	public void assureModule(Module module) {
		if (!modulePort.containsKey(module.getContext())) {
			HashMap<String, ModuleBean> builder = HashMap.empty();
			builder = builder.putAll(modulePort);
			builder = builder.put(module.getContext(), ModuleBean.builder().host(getHost(module)).port(getPort(module)).build());
			modulePort = builder;
		}

	}

	private String getHost(Module module) {
		Try.CheckedSupplier<String,Exception> s = ()->InetAddress.getLocalHost().getHostName();
		try{
			return Optional.ofNullable(properties.get(module.getContext() + ".host")).orElse(s.get()).toString();
		}catch(Throwable e){
			 throw new RuntimeException(e);
		}
	}

	private int getPort(Module module) {

		return Integer.valueOf(Optional.ofNullable(properties.get(module.getContext() + ".port")).orElse(nextPort++).toString());
	}
}