package com.aol.micro.server.dist.lock;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LockController {

	private final DistributedLockService lock;
	private final List<LockKeyProvider> lockKeyProviders;
	private Map<String, String> nameKeyMap;

	@Autowired
	public LockController(DistributedLockService lock, List<LockKeyProvider> lockKeyProviders) {
		this.lock = lock;
		this.lockKeyProviders = lockKeyProviders;
	}

	@PostConstruct
	public void init() {
		nameKeyMap = lockKeyProviders.stream().collect(Collectors.toMap(LockKeyProvider::getLockName, LockKeyProvider::getKey));
	}

	public boolean acquire(String lockName) {
		return lock.tryLock(nameKeyMap.get(lockName));
	}
}
