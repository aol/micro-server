package com.oath.micro.server.module;

import lombok.Getter;
import lombok.Builder;



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
