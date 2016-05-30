package com.aol.micro.server.distlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LockController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private volatile boolean isLeader;
	
	@Autowired(required=false)
	DistributedLockService lock;	

	public boolean acquire(LockKeyProvider keyProvider) {
		if (lock.tryLock(keyProvider.getKey())) {
			isLeader = true;
		} else {
			isLeader = false;
		}
		return isLeader;
	}

	public boolean acquireAndLog(LockKeyProvider keyProvider) {
		if (lock.tryLock(keyProvider.getKey())) {
			isLeader = true;
		} else {
			logger.info("Not leader, doing nothing");
			isLeader = false;
		}
		return isLeader;
	}
}
