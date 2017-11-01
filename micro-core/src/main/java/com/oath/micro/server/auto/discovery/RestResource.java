package com.aol.micro.server.auto.discovery;



public interface RestResource {

	/**
	 * @return true if singleton
	 */
	default boolean isSingleton(){
		return false;
	}
	
}
