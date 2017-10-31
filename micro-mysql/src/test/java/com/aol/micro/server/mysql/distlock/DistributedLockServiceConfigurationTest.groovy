package com.aol.micro.server.mysql.distlock

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

import org.junit.Test

class DistributedLockServiceConfigurationTest {

/**
	private DistributedLockServiceConfiguration configuration = new DistributedLockServiceConfiguration()

	@Test
	public void "test couchbase"() {
		configuration.distributedLockToUse = "couchbase"
		configuration.keyTimeoutInSeconds = 100
		assertThat(configuration.distributedLockService() instanceof DistributedLockServiceCouchbaseImpl, is(true));
	}

	@Test
	public void "test mysql"() {
		configuration.distributedLockToUse = "mysql"
		configuration.keyTimeoutInSeconds = 100
		assertThat(configuration.distributedLockService() instanceof DistributedLockServiceMySqlImpl, is(true));
	}
	 **/
}
