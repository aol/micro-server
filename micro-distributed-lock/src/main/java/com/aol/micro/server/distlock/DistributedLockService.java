
package com.aol.micro.server.distlock;


public interface DistributedLockService {

	boolean tryLock(String key);

	boolean tryReleaseLock(String key);

}
