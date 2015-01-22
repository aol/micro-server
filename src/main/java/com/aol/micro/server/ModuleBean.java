package com.aol.micro.server;

import java.util.Map;

import javax.servlet.Filter;

import lombok.Getter;
import lombok.experimental.Builder;



@Getter
public class ModuleBean {

	private final int port;
	private final String host;
	private final Module module;
	
	
	
	@Builder
	public ModuleBean(int port, String host, Module module) {
		
		this.port = port;
		this.host = host;
		this.module = module;
		
	}
	
	
}
