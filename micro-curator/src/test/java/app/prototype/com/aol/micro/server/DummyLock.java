package app.prototype.com.aol.micro.server;

import org.springframework.stereotype.Component;

import com.oath.micro.server.dist.lock.DistributedLockService;

@Component
public class DummyLock implements DistributedLockService {

	@Override
	public boolean tryLock(String key) {
		return false;
	}

	@Override
	public boolean tryReleaseLock(String key) {
		return false;
	}

}
