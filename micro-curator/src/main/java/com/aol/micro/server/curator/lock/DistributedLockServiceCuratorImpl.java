package com.aol.micro.server.curator.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.utility.DistributedLockService;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

/**
 * DistributedLockService suitable for single threaded use only
 *
 */
@Wither
@AllArgsConstructor
public class DistributedLockServiceCuratorImpl implements DistributedLockService, ConnectionStateListener {

   

    private final ConcurrentMap<String, InterProcessMutex> locks = new ConcurrentHashMap<>();

    private final String basePath;

    private final CuratorFramework curatorFramework;

    private final int timeout;

    private static final Logger logger = LoggerFactory.getLogger(DistributedLockServiceCuratorImpl.class);

    public DistributedLockServiceCuratorImpl(CuratorFramework curatorFramework, String basePath, int timeout)
            throws Exception {
        this.curatorFramework = curatorFramework;
        this.basePath = basePath;
        this.timeout = timeout;
        createIfNotExists(basePath);
    }

    private void createIfNotExists(String path) throws Exception {
        if (curatorFramework.checkExists().forPath(path) == null) {
            curatorFramework.create().creatingParentContainersIfNeeded().forPath(path, new byte[0]);
        }
    }

    @Override
    public boolean tryLock(String key) {
        try {
            InterProcessMutex mutex = locks.computeIfAbsent(key,
                    __ -> new InterProcessMutex(curatorFramework, String.join("/", basePath, key)));
            
            
            boolean owned = mutex.isAcquiredInThisProcess();
            if(owned) {
                return true;
            } else {
                mutex.acquire(timeout, TimeUnit.MILLISECONDS);
            }
            return mutex.isAcquiredInThisProcess();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean tryReleaseLock(String key) {
    	return Optional.ofNullable(locks.get(key)).map(c -> {
            try {
                c.release();
                return true;
            } catch (Exception e) {
                return false;
            }
        }).orElse(false);
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
    	
        switch (newState) {
        case LOST:
        case SUSPENDED:

            Collection<InterProcessMutex> oldLocks = new ArrayList<>(locks.values());
            locks.clear();

            oldLocks.stream().parallel().forEach(lock -> {
                try {
                    lock.release();
                } catch (Exception e) {
                    logger.trace("Can't release lock on " + newState);
                }
            });
            break;
        default:
        }
    }

}
