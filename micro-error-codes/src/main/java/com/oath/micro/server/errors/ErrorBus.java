package com.oath.micro.server.errors;

import com.google.common.eventbus.EventBus;

public class ErrorBus {

	private static EventBus errorBus;

	public static synchronized void setErrorBus(final EventBus bus){
		errorBus = bus;
	}
	public void post(final Object o){
		if(errorBus!=null) {
			errorBus.post(o);
		}
	}
}
