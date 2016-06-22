package com.aol.micro.server.dist.lock;

public class DistributedLockManager {

	private final DistributedLockService distributedLockService;
	private final String lockKey;

	public DistributedLockManager(DistributedLockService distributedLockService, String lockKey) {
		this.distributedLockService = distributedLockService;
		this.lockKey = lockKey;
	}

	public boolean isMainProcess() {
		return distributedLockService.tryLock(lockKey);
	}

}
