package app.com.oath.micro.server.dist.lock.rest;

import org.springframework.stereotype.Component;

import com.oath.micro.server.dist.lock.DistributedLockService;

@Component
public class DummyLock implements DistributedLockService {

    @Override
    public boolean tryLock(String key) {
        if (key.equals("dummy-key")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean tryReleaseLock(String key) {
        return false;
    }

}
