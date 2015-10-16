package com.aol.micro.server.couchbase.distributed.locking

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat

import org.junit.Test

class DistributedLockServiceConfigurationTest {


	private DistributedLockServiceConfiguration configuration = new DistributedLockServiceConfiguration()

	@Test
	public void "test couchbase"() {
		
		configuration.keyTimeoutInSeconds = 100
		assertThat(configuration.distributedLockService() instanceof DistributedLockServiceCouchbaseImpl, is(true));
	}

	
}
