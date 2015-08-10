package com.aol.micro.server.kafka.producer;

import java.util.Properties;

import javax.annotation.PostConstruct;

import kafka.producer.ProducerConfig;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerConfig {

	private final String brokerList;
	private final String serializerClass;
	private final String partitionerClass;
	private final String requestRequiredAcks;

	@Getter
	private ProducerConfig config;

	@Autowired
	public KafkaProducerConfig(@Value("${metadata.broker.list:localhost:9092}") String brokerList,
			@Value("${serializer.class:kafka.serializer.DefaultEncoder}") String serializerClass,
			@Value("${partitioner.class:kafka.producer.DefaultPartitioner}") String partitionerClass,
			@Value("${request.required.acks:1}") String requestRequiredAcks) {
		this.brokerList = brokerList;
		this.serializerClass = serializerClass;
		this.partitionerClass = partitionerClass;
		this.requestRequiredAcks = requestRequiredAcks;
	}

	@PostConstruct
	public void initProperties() {
		Properties props = new Properties();
		props.put("metadata.broker.list", brokerList);
		props.put("serializer.class", serializerClass);
		props.put("partitioner.class", partitionerClass);
		props.put("request.required.acks", requestRequiredAcks);
		config = new ProducerConfig(props);
	}

}
