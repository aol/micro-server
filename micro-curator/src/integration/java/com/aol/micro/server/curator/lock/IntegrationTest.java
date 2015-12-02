package com.aol.micro.server.curator.lock;

import java.io.IOException;
import java.util.Properties;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.utility.DistributedLockService;

public class IntegrationTest {

	private CuratorDistributorLockServiceProvider provider;

	private ZooKeeperServerMain zooKeeperServer;
	
	@Before
	public void initialize() {
		
		Properties startupProperties = new Properties();
		
		startupProperties.put("dataDir", "/tmp/zookeeper");
		startupProperties.put("clientPort", "2181");

		
		QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
		try {
		    quorumConfiguration.parseProperties(startupProperties);
		} catch(Exception e) {
		    throw new RuntimeException(e);
		}

		zooKeeperServer = new ZooKeeperServerMain();
		final ServerConfig configuration = new ServerConfig();
		configuration.readFrom(quorumConfiguration);

		new Thread() {
		    public void run() {
		        try {
		            zooKeeperServer.runFromConfig(configuration);
		        } catch (IOException e) {
		        	e.printStackTrace();
		        }
		    }
		}.start();
		
		
		provider = new CuratorDistributorLockServiceProvider("localhost", 1000, 3, "/test");
	}
	
	@Test
	public void lock() {
		
		final String lockName = "test2";
		
		DistributedLockService lock = provider.getDistributedLock(1000);
		Assert.assertTrue(lock.tryLock(lockName));
		DistributedLockService lock2 = provider.getDistributedLock(1000);
		Assert.assertFalse(lock2.tryLock(lockName));
	}

}
