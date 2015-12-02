package com.aol.micro.server.curator.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.aol.micro.server.utility.DistributedLockService;

public class CuratorDistributorLockServiceProvider {

	private CuratorFramework curatorFramework;

	private String lockBasePath;
	
	@Autowired
	public CuratorDistributorLockServiceProvider(
			@Value("zookeeper.connectionString:#null") String zookeeperConnectionString,
			@Value("zookeeper.sleepTime:1000") int sleepTime, @Value("zookeeper.maxRetries:1000") int maxRetries,
			@Value("curator.lockBasePath") String lockBasePath) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(sleepTime, maxRetries);
		this.curatorFramework = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
		this.lockBasePath = lockBasePath;
		
		curatorFramework.start();
	}
/**
 * 
 * @param name - lock name
 * @param timeout - timeout for locking in milliseconds
 * @return DistributedLockService backed up by curator instance
 */
	public DistributedLockService getDistributedLock(String name, int timeout) {
		return new DistributedLockServiceCuratorImpl(curatorFramework, lockBasePath, timeout);
	}

}
