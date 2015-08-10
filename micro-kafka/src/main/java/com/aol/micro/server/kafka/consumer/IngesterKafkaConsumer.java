package com.aol.micro.server.kafka.consumer;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import com.aol.advertising.eventlog.record.EventLogRecord;
import com.aol.advertising.lana.common.exceptions.ErrorCode;
import com.aol.advertising.lana.common.loggers.FunctionalAreaPrefix;
import com.aol.advertising.lana.common.loggers.LanaLogger;
import com.aol.advertising.lana.provider.SingleRecordProcessor;

public class IngesterKafkaConsumer implements Runnable {

	private final LanaLogger logger = LanaLogger.getLogger(FunctionalAreaPrefix.INGESTER_LOG_PROCESSOR, getClass());

	private KafkaStream stream;
	private String topic;
	private SingleRecordProcessor singleRecordProcessor;

	public IngesterKafkaConsumer(KafkaStream stream, String topic, SingleRecordProcessor singleRecordProcessor) {
		this.topic = topic;
		this.stream = stream;
		this.singleRecordProcessor = singleRecordProcessor;
	}

	public void run() {
		logger.info("Runing consumer Thread for topic {}", topic);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		while (it.hasNext()) {

			byte[] bytesReceived = it.next().message();

			EventLogRecord eventLogRecord;
			try {
				eventLogRecord = EventLogRecordDeserializer.deserialize(bytesReceived);
				logger.debug("EventLogRecord received from topic {} : {}", topic, eventLogRecord.getRecordTypeName());
				singleRecordProcessor.processRecord(eventLogRecord);
				logger.debug("EventLogRecord processed successfully from topic {} : {}", topic, eventLogRecord.getRecordTypeName());
			} catch (Exception e) {
				logger.error(ErrorCode.UNABLE_TO_PROCESS_IMPRESSION, e, "Error trying to deserialize records");
			}

		}
		logger.info("Shutting down Thread for topic : {}", topic);
	}

}