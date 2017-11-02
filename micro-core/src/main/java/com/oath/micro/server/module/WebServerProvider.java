package com.oath.micro.server.module;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebServerProvider <PROVIDER> {

	private final PROVIDER provider;
	
	public PROVIDER getHttpServer(){
		return provider;
	}
}
