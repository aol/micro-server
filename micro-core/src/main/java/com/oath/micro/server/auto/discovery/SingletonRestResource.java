package com.oath.micro.server.auto.discovery;

public interface SingletonRestResource extends RestResource{
	default boolean isSingleton(){
		return true;
	}
}
