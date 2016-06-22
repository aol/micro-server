package com.aol.micro.server.dist.lock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LockController {

	private final DistributedLockService lock;
	private final Map<String, String> nameKeyMap;

	@Autowired(required = false)
	public LockController(DistributedLockService lock, List<LockKeyProvider> lockKeyProviders) {
		this.lock = lock;
		this.nameKeyMap = lockKeyProviders.stream()
				.collect(Collectors.toMap(LockKeyProvider::getLockName, LockKeyProvider::getKey));
	}

	@Autowired(required = false)
	public LockController(DistributedLockService lock) {
		this.lock = lock;
		this.nameKeyMap = new HashMap<String, String>();
	}

	public boolean acquire(String lockName) {
		String key = nameKeyMap.get(lockName);
		if (key != null) {
			return lock.tryLock(nameKeyMap.get(lockName));
		} else {
			return false;
		}
	}
}
