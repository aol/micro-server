package com.aol.micro.server.kafka.producer;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kafka.javaapi.producer.Producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class KafkaProducerManager implements ServletContextListener {

	private final KafkaProducerConfig config;
	private final int poolSize;
	private final KafkaProducer kafkaProducer;

	private ExecutorService executor;
	private List<Producer<String, byte[]>> producers;

	@Autowired
	public KafkaProducerManager(KafkaProducerConfig config, @Value("${sharder.service.kafka.producer.pool.size:20}") int poolSize,
			KafkaProducer kafkaProducer) {
		this.config = config;
		this.poolSize = poolSize;
		this.kafkaProducer = kafkaProducer;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		init();

		new Thread() {
			public void run() {
				CompletableFuture[] futures = producers.stream().map(CompletableFuture::completedFuture)
						.map(future -> future.thenAcceptAsync(kafkaProducer::produce, executor)).toArray(CompletableFuture[]::new);

				CompletableFuture.allOf(futures).join();

			};
		}.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		executor.shutdown();
		producers.stream().forEach(producer -> producer.close());
	}

	private void init() {
		producers = Lists.newArrayList();
		for (int i = 0; i < poolSize; i++) {
			Producer producer = new Producer<String, byte[]>(config.getConfig());
			producers.add(producer);
		}

		executor = Executors.newFixedThreadPool(poolSize);
	}
}
