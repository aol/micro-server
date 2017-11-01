package com.oath.micro.server.auto.discovery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class JaxRsResourceWrapper<T> {

	@Getter
	private final T resource;
	
	public static <T> JaxRsResourceWrapper<T> jaxRsResource(T t){
		return new JaxRsResourceWrapper<T>(t);
	}
}
