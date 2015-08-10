package com.aol.micro.server.kafka.consumer;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.aol.advertising.lana.provider.SingleRecordProcessor;

public class IngesterKafkaConsumerGroupServiceTest {

	private IngesterKafkaConsumerGroupService ingesterKafkaConsumerGroupService;
	private SingleRecordProcessor singleRecordProcessor;

	@Before
	public void init() {
		singleRecordProcessor = mock(SingleRecordProcessor.class);
		ingesterKafkaConsumerGroupService = new IngesterKafkaConsumerGroupService("a-dev-lanartb02.advertising.aol.com:2181/test", "0to30",
				"ingester-consumer-group-test", 1, singleRecordProcessor);
	}

	@Test
	public void testRun() {
		ingesterKafkaConsumerGroupService.contextInitialized(null);

	}

}