package com.aol.micro.server.curator.lock;

import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.utility.DistributedLockService;

/**
 * DistributedLockService suitable for single threaded use only
 *
 */
@Wither
@AllArgsConstructor
public class DistributedLockServiceCuratorImpl implements DistributedLockService, ConnectionStateListener {

	private boolean acquired;

	private InterProcessSemaphoreMutex curatorLock;

	private String lockName;

	private final String basePath;

	private final CuratorFramework curatorFramework;

	private final int timeout;

	private static final Logger logger = LoggerFactory.getLogger(DistributedLockServiceCuratorImpl.class);
	
	public DistributedLockServiceCuratorImpl(CuratorFramework curatorFramework, String basePath, int timeout) throws Exception {
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
		if (curatorLock == null) {
			lockName = key;
			curatorLock = new InterProcessSemaphoreMutex(curatorFramework, String.join("/", basePath, key));
			return acquire();
		} else if (lockName.equals(key)) {
			return acquire();
		} else {
			throw new IllegalArgumentException(
					String.format("Lock can't change the name old:%s, new:%s", lockName, key));
		}
	}

	private synchronized boolean acquire() {
		try {
			return acquired = acquired ? acquired : curatorLock.acquire(timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean tryReleaseLock(String key) {
		
		try {
			curatorLock.release();
			return true;
		} catch (Exception e) {
			logger.warn("Can't release lock", e);
			return false;
		}finally{
			setAcquired(false);
		}
	}

	@Override
	public void stateChanged(CuratorFramework client, ConnectionState newState) {
		switch (newState) {
		case LOST:
		case SUSPENDED:
			setAcquired(false);
			break;
		default:
		}
	}

	private synchronized boolean isAcquired() {
		return acquired;
	}

	private synchronized void setAcquired(boolean acquired) {
		this.acquired = acquired;
	}

}
