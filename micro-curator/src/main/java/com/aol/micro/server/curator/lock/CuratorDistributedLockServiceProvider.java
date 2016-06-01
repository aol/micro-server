package com.aol.micro.server.curator.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aol.micro.server.dist.lock.DistributedLockService;

@Component
public class CuratorDistributedLockServiceProvider {

	final private CuratorFramework curatorFramework;

	final private String lockBasePath;
	
	@Autowired
	public CuratorDistributedLockServiceProvider(
			@Value("${zookeeper.connectionString:localhost:12181}") String zookeeperConnectionString,
			@Value("${zookeeper.sleepTime:1000}") String sleepTime, @Value("${zookeeper.maxRetries:3}") String maxRetries,
			@Value("${curator.lockBasePath:/tmp/zookeeper}") String lockBasePath) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(Integer.valueOf(sleepTime), Integer.valueOf(maxRetries));
		this.curatorFramework = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
		this.lockBasePath = lockBasePath;
		
		curatorFramework.start();
	}
	/**
	 * @param timeout - timeout for locking in milliseconds
	 * @return DistributedLockService backed up by curator instance
	 */
	public DistributedLockService getDistributedLock(int timeout)  {
		try {
			return new DistributedLockServiceCuratorImpl(curatorFramework, lockBasePath, timeout);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
