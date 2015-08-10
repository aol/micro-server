
package com.aol.micro.server.couchbase.distributed.locking;


public interface DistributedLockService {

	boolean tryLock(String key);

	boolean tryReleaseLock(String key);

}
