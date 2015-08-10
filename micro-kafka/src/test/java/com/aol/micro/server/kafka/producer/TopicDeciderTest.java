package com.aol.micro.server.kafka.producer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TopicDeciderTest {

	private TopicDecider topicDecider = new TopicDecider();

	@Test
	public void testGetTopic() {
		assertThat(topicDecider.getTopic(0), is("0to30"));
		assertThat(topicDecider.getTopic(1), is("0to30"));
		assertThat(topicDecider.getTopic(29 * 60 + 59), is("0to30"));

		assertThat(topicDecider.getTopic(30 * 60), is("30to60"));
		assertThat(topicDecider.getTopic(30 * 60 + 1), is("30to60"));
		assertThat(topicDecider.getTopic(59 * 60 + 59), is("30to60"));

		assertThat(topicDecider.getTopic(60 * 60), is("0to30"));
		assertThat(topicDecider.getTopic(60 * 60 + 1), is("0to30"));
		assertThat(topicDecider.getTopic(89 * 60 + 59), is("0to30"));

		assertThat(topicDecider.getTopic(90 * 60), is("30to60"));
		assertThat(topicDecider.getTopic(90 * 60 + 1), is("30to60"));
		assertThat(topicDecider.getTopic(119 * 60 + 59), is("30to60"));

	}

}
