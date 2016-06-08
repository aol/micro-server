
package com.aol.micro.server.dist.lock;


public interface DistributedLockService {

	boolean tryLock(String key);

	boolean tryReleaseLock(String key);

}
