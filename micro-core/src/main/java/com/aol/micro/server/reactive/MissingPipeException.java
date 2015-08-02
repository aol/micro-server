package com.aol.micro.server.reactive;

public class MissingPipeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingPipeException(String message){
		super(message);
	}
}
