package com.aol.micro.server;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Environment {

	private final Map<String, ModuleBean> modulePort;

	@Autowired
	public Environment(List<ModuleBean> modules) {
		modulePort = modules.stream().collect(Collectors.toMap(key -> key.getModule().getContext(), value -> value));
	}

	public ModuleBean getModuleBean(Module module) {
		return modulePort.get(module.getContext());
	}
}