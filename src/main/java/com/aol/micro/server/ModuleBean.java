package com.aol.micro.server;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;

import com.aol.micro.server.utility.UsefulStaticMethods;



@Getter
public class ModuleBean {

	private final int port;
	private final String host;
	private final Module module;
	private final Optional<String> mapping;
	@Builder
	public ModuleBean(int port, String host, Module module,
			Optional<String> mapping) {
		
		this.port = port;
		this.host = host;
		this.module = module;
		this.mapping = UsefulStaticMethods.either(mapping,Optional.<String>empty());
	}
	
	
}
