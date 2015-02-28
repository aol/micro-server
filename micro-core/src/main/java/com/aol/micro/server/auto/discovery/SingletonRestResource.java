package com.aol.micro.server.auto.discovery;

public interface SingletonRestResource extends RestResource{
	default boolean isSingleton(){
		return true;
	}
}
