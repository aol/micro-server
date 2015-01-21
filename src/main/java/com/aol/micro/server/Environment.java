package com.aol.micro.server;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.inject.Qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

@Component
public class Environment {

	private volatile Map<String, ModuleBean> modulePort;
	private final Properties properties;

	@Autowired(required=false)
	public Environment(Properties propertyFactory,List<ModuleBean> modules) {
		modulePort = modules.stream().collect(Collectors.toMap(key -> key.getModule().getContext(), value -> value));
		this.properties = propertyFactory;
	}
	@Autowired(required=false)
	public Environment(Properties propertyFactory) {
		modulePort = ImmutableMap.of();
		this.properties = propertyFactory;

	}

	public ModuleBean getModuleBean(Module module) {
		return modulePort.get(module.getContext());
	}
	public void assureModule(Module module) {
		if(!modulePort.containsKey(module.getContext())){
			Map<String, ModuleBean> builder = Maps.newHashMap();
			builder.putAll(modulePort);
			builder.put(module.getContext(),ModuleBean.builder().port(getPort(module)).build());
			modulePort = ImmutableMap.copyOf(builder);
		}
			
		
	}
	private int getPort(Module module) {
		
		return Integer.valueOf(Optional.ofNullable(
				properties.get(module.getContext()+".port"))
				.orElse("8080").toString());
	}
}