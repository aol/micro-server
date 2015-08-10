package com.aol.micro.server.couchbase.distributed.locking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistributedLockServiceConfiguration {

	@Value("${key.timeout.in.seconds:120}")
	private int keyTimeoutInSeconds;

	@Value("${distributed.lock.to.use:couchbase}")
	private String distributedLockToUse;

	@Bean
	public DistributedLockService distributedLockService() {
		return new DistributedLockServiceCouchbaseImpl(keyTimeoutInSeconds);

	}

}
