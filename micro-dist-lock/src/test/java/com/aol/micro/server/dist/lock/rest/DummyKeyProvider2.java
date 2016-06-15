package com.aol.micro.server.dist.lock.rest;

import org.springframework.stereotype.Component;

import com.aol.micro.server.dist.lock.LockKeyProvider;

@Component
public class DummyKeyProvider2 implements LockKeyProvider {
	
	@Override
	public String getKey() {
		return "key2";
	}
	
	@Override
	public String getLockName() {
		return "dummyKeyProvider2";
	}

}
