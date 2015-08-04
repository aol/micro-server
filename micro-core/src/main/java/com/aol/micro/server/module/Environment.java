package com.aol.micro.server.module;

import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.jooq.lambda.fi.util.function.CheckedSupplier;
import org.pcollections.HashTreePMap;

public class Environment {

	private volatile Map<String, ModuleBean> modulePort;
	private final Properties properties;
	private volatile int nextPort = 8080;

	public Environment(Properties propertyFactory, Collection<ModuleBean> modules) {
		modulePort = modules.stream().collect(Collectors.toMap(key -> key.getModule().getContext(), value -> value));
		this.properties = propertyFactory;
	}

	public Environment(Properties propertyFactory) {
		modulePort = HashTreePMap.empty();
		this.properties = propertyFactory;

	}

	public ModuleBean getModuleBean(Module module) {
		return modulePort.get(module.getContext());
	}

	public void assureModule(Module module) {
		if (!modulePort.containsKey(module.getContext())) {
			Map<String, ModuleBean> builder = new HashMap<>();
			builder.putAll(modulePort);
			builder.put(module.getContext(), ModuleBean.builder().host(getHost(module)).port(getPort(module)).build());
			modulePort = HashTreePMap.from(builder);
		}

	}

	private String getHost(Module module) {
		CheckedSupplier<String> s = ()->InetAddress.getLocalHost().getHostName();
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