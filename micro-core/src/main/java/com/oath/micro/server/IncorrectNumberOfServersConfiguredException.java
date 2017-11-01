package com.oath.micro.server;

public class IncorrectNumberOfServersConfiguredException extends RuntimeException {

	public IncorrectNumberOfServersConfiguredException(String message){
		super(message);
	}
}
