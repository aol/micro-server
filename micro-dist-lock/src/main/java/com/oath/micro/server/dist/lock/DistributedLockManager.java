package com.oath.micro.server.dist.lock;

import lombok.Getter;

public class DistributedLockManager {

    private final DistributedLockService distributedLockService;
    @Getter
    private final String key;

    public DistributedLockManager(DistributedLockService distributedLockService, String lockKey) {
        this.distributedLockService = distributedLockService;
        this.key = lockKey;
    }

    public boolean isMainProcess() {
        return distributedLockService.tryLock(key);
    }

}
