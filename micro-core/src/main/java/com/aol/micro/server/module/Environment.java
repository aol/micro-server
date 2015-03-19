package com.aol.micro.server.module;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class Environment {

	private volatile Map<String, ModuleBean> modulePort;
	private final Properties properties;
	private volatile int nextPort = 8080;

	public Environment(Properties propertyFactory, Collection<ModuleBean> modules) {
		modulePort = modules.stream().collect(Collectors.toMap(key -> key.getModule().getContext(), value -> value));
		this.properties = propertyFactory;
	}

	public Environment(Properties propertyFactory) {
		modulePort = ImmutableMap.of();
		this.properties = propertyFactory;

	}

	public ModuleBean getModuleBean(Module module) {
		return modulePort.get(module.getContext());
	}

	public void assureModule(Module module) {
		if (!modulePort.containsKey(module.getContext())) {
			Map<String, ModuleBean> builder = Maps.newHashMap();
			builder.putAll(modulePort);
			builder.put(module.getContext(), ModuleBean.builder().port(getPort(module)).build());
			modulePort = ImmutableMap.copyOf(builder);
		}

	}

	private int getPort(Module module) {

		return Integer.valueOf(Optional.ofNullable(properties.get(module.getContext() + ".port")).orElse(nextPort++).toString());
	}
}