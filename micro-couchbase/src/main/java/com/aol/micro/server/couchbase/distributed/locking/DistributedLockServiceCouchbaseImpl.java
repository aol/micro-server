package com.aol.micro.server.couchbase.distributed.locking;

import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import net.spy.memcached.internal.OperationFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;

import com.aol.micro.server.utility.DistributedLockService;
import com.couchbase.client.CouchbaseClient;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class DistributedLockServiceCouchbaseImpl implements DistributedLockService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Getter
	private volatile ImmutableMap<String, LockData> lockDataMap = ImmutableMap.of();
	private CouchbaseClient couchbaseClient;
	private final int keyTimeoutInSeconds;

	public DistributedLockServiceCouchbaseImpl(int keyTimeoutInSeconds) {
		this.keyTimeoutInSeconds = keyTimeoutInSeconds;

	}

	@Autowired
	@Qualifier("persistentCouchbaseClient")
	public void setCouchbaseClient(CouchbaseClient couchbaseClient) {
		this.couchbaseClient = couchbaseClient;
	}

	@Override
	public boolean tryLock(String key) {
		addKeyToMapIfNotPresent(key);
		boolean hasLock = lockDataMap.get(key).isHasLock();
		if (hasLock) {
			if (!canAddKey(key) && !stillTheSameValue(key)) {
				// Could still have the lock but needs to check
				// At this point, we are sure the key is in couchbase
				hasLock = false;
			}
		} else if (canAddKey(key)) {
			hasLock = true;
		}
		rebuildLockDataMap(lockDataMap.get(key).withHasLock(hasLock));
		return hasLock;
	}

	@Override
	public boolean tryReleaseLock(String key) {
		LockData lockData = lockDataMap.get(key);
		if (lockData != null && lockData.isHasLock()) {

			//At this point, we should change the hasLock to false immediately 
			//So that we don't renew this lock anymore
			rebuildLockDataMap(lockData.withHasLock(false));

			if (stillTheSameValue(key)) {//we don't want to delete the key added by other process
				return canDeleteKey(key);
			}
		}
		return false;
	}

	@Scheduled(fixedDelayString = "${renew.lock.delay:5000}")
	public void renewLock() {
		Map<String, LockData> changedLockMap = Maps.newHashMap();
		lockDataMap.keySet().forEach((key) -> {
			LockData lockData = lockDataMap.get(key);
			if (lockData.isHasLock() && !canAddKey(key) && !stillTheSameValue(key)) {
				changedLockMap.put(key, lockData.withHasLock(false));
			}
		});

		changedLockMap.values().stream().forEach((lockData) -> rebuildLockDataMap(lockData));

	}

	private boolean canDeleteKey(String key) {
		try {
			return couchbaseClient.delete(key).getStatus().isSuccess();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * If can add the key then it means there were no such key in couchbase
	 * so this process get the lock
	 * 
	 * If can not add the key then it means the key is already in couchbase
	 * It also means the current process could already have the lock or another 
	 * process has the lock	 
	 * 
	 */
	private boolean canAddKey(String key) {
		try {
			OperationFuture<Boolean> res = couchbaseClient.add(key, keyTimeoutInSeconds, lockDataMap.get(key).getValue());
			return res.get();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * If the value is the same. It means the current process still has the lock
	 * otherwise it loses the lock 
	 */
	private boolean stillTheSameValue(String key) {
		try {
			String valueInCouchbase = (String) couchbaseClient.getAndTouch(key, keyTimeoutInSeconds).getValue();
			return valueInCouchbase.equals(lockDataMap.get(key).getValue());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		}
	}

	private synchronized void addKeyToMapIfNotPresent(String key) {
		LockData value = lockDataMap.get(key);
		if (value == null) {
			Map<String, LockData> map = Maps.newHashMap();
			map.putAll(lockDataMap);
			map.put(key, new LockData(key, UUID.randomUUID().toString(), false));
			lockDataMap = ImmutableMap.copyOf(map);
		}
	}

	private synchronized void rebuildLockDataMap(LockData lockData) {
		Map<String, LockData> map = Maps.newHashMap();
		map.putAll(lockDataMap);
		map.put(lockData.getKey(), lockData);
		lockDataMap = ImmutableMap.copyOf(map);

	}
}
