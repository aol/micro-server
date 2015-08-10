package com.aol.micro.server.kafka.consumer;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aol.advertising.lana.common.loggers.FunctionalAreaPrefix;
import com.aol.advertising.lana.common.loggers.LanaLogger;
import com.aol.advertising.lana.provider.SingleRecordProcessor;
import com.aol.simple.react.stream.simple.SimpleReact;
import com.google.common.collect.Lists;

@Component
public class IngesterKafkaConsumerGroupService implements ServletContextListener {

	private final LanaLogger logger = LanaLogger.getLogger(FunctionalAreaPrefix.INGESTER_LOG_PROCESSOR, getClass());

	private List<String> topicsNames;

	private ConsumerConnector consumerConnector;

	private Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap;
	private ExecutorService executor;

	private SingleRecordProcessor singleRecordProcessor;

	@Autowired
	public IngesterKafkaConsumerGroupService(@Value("${kafka.zookeeper:a-dev-lanartb02.advertising.aol.com:2181/test}") String zookeeper,
			@Value("${kafka.topics:0to30,30to60}") String topics, @Value("${kafka.group.id:ingester-consumer-group}") String groupId,
			@Value("${kafka.consumers.threads:10}") Integer consumersThreads, SingleRecordProcessor singleRecordProcessor) {
		this.singleRecordProcessor = singleRecordProcessor;
		consumerConnector = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig(zookeeper, groupId));
		topicsNames = Lists.newArrayList(topics.split(","));
		consumerMap = consumerConnector.createMessageStreams(topicsNames.stream().collect(Collectors.toMap(key -> key, value -> new Integer(1))));
		executor = Executors.newFixedThreadPool(consumersThreads);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("Stopping kafka consumers");
		shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Starting kafka consumers");
		run();
	}

	private void run() {
		new SimpleReact().reactToCollection(topicsNames).peek(this::consumeTopic);
	}

	private void consumeTopic(String topic) {

		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

		for (final KafkaStream stream : streams) {
			executor.submit(new IngesterKafkaConsumer(stream, topic, singleRecordProcessor));
		}
	}

	public void shutdown() {
		if (consumerConnector != null)
			consumerConnector.shutdown();
		if (executor != null)
			executor.shutdown();
		try {
			if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
				logger.warn("Timed out waiting for consumer threads to shut down, exiting uncleanly");
			}
		} catch (InterruptedException e) {
			logger.warn("Interrupted during shutdown, exiting uncleanly");
		}
	}

	private ConsumerConfig createConsumerConfig(String zookeeper, String groupId) {
		Properties props = new Properties();
		props.put("zookeeper.connect", zookeeper);
		props.put("group.id", groupId);
		return new ConsumerConfig(props);
	}

}