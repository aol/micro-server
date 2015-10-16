package com.aol.micro.server.couchbase.distributed.locking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.utility.DistributedLockService;

@Configuration
public class DistributedLockServiceConfiguration {

	@Value("${key.timeout.in.seconds:120}")
	private int keyTimeoutInSeconds;

	@Bean
	public DistributedLockService distributedLockService() {
		return new DistributedLockServiceCouchbaseImpl(keyTimeoutInSeconds);

	}

}
