package com.aol.micro.server.kafka.producer;

import org.springframework.stereotype.Component;

@Component
public class TopicDecider {

	private final static String TOPIC1 = "0to30";
	private final static String TOPIC2 = "30to60";
	private final static long SECONDS_IN_30_MINUTES = 1800;

	public String getTopic(long seconds) {
		long halfHourPeriod = seconds / SECONDS_IN_30_MINUTES;
		long topicNumber = halfHourPeriod % 2;
		return topicNumber == 0 ? TOPIC1 : TOPIC2;
	}

}
