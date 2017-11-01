package com.oath.micro.server.module;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JaxRsProvider<PROVIDER> {

	private final PROVIDER provider;
	
	public PROVIDER getJaxRsConfig(){
		return provider;
	}
}
