package com.aol.micro.server.kafka.producer;

import java.util.Optional;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.advertising.eventlog.record.BidRecord;
import com.aol.advertising.eventlog.record.EventLogRecord;
import com.aol.advertising.eventlog.record.ImpressionRecord;
import com.aol.advertising.lana.sharderservice.config.QueuesConfig;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class KafkaProducer {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final EventLogRecordToByteArrayConverter converter;
	private final TopicDecider topicDecider;
	private final QueuesConfig queuesConfig;

	@Autowired
	public KafkaProducer(EventLogRecordToByteArrayConverter converter, TopicDecider topicDecider, QueuesConfig queuesConfig) {
		this.converter = converter;
		this.topicDecider = topicDecider;
		this.queuesConfig = queuesConfig;
	}

	public void produce(Producer producer) {
		try {
			while (true) {
				EventLogRecord record = queuesConfig.getEventQueue().take();
				sendData(record, producer);
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void sendData(EventLogRecord record, Producer producer) {
		Optional<byte[]> bytesOp = converter.convert(record);
		bytesOp.ifPresent(bytes -> {
			try {
				KeyedMessage<String, byte[]> data = new KeyedMessage<String, byte[]>(topicDecider.getTopic(getTimestamp(record)), bytes);
				producer.send(data);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		});
	}

	private long getTimestamp(EventLogRecord record) {
		if (record instanceof ImpressionRecord) {
			return ((ImpressionRecord) record).getTimestamp();
		}

		if (record instanceof BidRecord) {
			return ((BidRecord) record).getTimestamp();
		}

		throw new RuntimeException("Event log record type is not supported");
	}

}
