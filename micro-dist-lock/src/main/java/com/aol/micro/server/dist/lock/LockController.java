package com.aol.micro.server.dist.lock;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LockController {

	private String combinedKey;
	
	@Autowired(required=false)
	DistributedLockService lock;
	
	@Autowired(required=false) 
	List<LockKeyProvider> lockKeyProviders;

	@PostConstruct
	public void init() {
		combinedKey = Optional.ofNullable(lockKeyProviders).map(providers -> providers.stream().map(keyProvider -> keyProvider.getKey()).collect(Collectors.joining(""))).orElse(null);
	}	

	public boolean acquire() {
		return Optional.ofNullable(combinedKey).map(key -> Optional.ofNullable(lock).map(service -> service.tryLock(key)).orElse(false)).orElse(false);		
	}
}
