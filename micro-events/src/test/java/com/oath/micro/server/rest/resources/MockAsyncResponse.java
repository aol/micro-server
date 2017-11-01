package com.oath.micro.server.rest.resources;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.TimeoutHandler;

public class MockAsyncResponse<T> implements AsyncResponse {

	volatile Object response;
	
	public T response(){
		while(response==null)
			LockSupport.parkNanos(0l);
		return (T)response;
	}
	@Override
	public boolean resume(Object response) {
		this.response= response;
		return false;
	}

	@Override
	public boolean resume(Throwable response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancel(int retryAfter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancel(Date retryAfter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setTimeout(long time, TimeUnit unit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTimeoutHandler(TimeoutHandler handler) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Class<?>> register(Class<?> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Class<?>, Collection<Class<?>>> register(Class<?> callback,
			Class<?>... callbacks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Class<?>> register(Object callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Class<?>, Collection<Class<?>>> register(Object callback,
			Object... callbacks) {
		// TODO Auto-generated method stub
		return null;
	}

}
