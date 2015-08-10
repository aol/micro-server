package com.aol.micro.server.kafka.consumer;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigInteger;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import org.junit.Before;
import org.junit.Test;

import com.aol.advertising.lana.provider.SingleRecordProcessor;

public class IngesterKafkaConsumerTest {

	private IngesterKafkaConsumer ingesterKafkaConsumer;
	private final String topic = "test-topic";
	private KafkaStream stream;
	private SingleRecordProcessor singleRecordProcessor;

	@Before
	public void init() {

		stream = mock(KafkaStream.class);
		singleRecordProcessor = mock(SingleRecordProcessor.class);
		ingesterKafkaConsumer = new IngesterKafkaConsumer(stream, topic, singleRecordProcessor);
	}

	@Test
	public void testRunEmptyStream() {
		ConsumerIterator<byte[], byte[]> it = mock(ConsumerIterator.class);
		when(stream.iterator()).thenReturn(it);
		when(it.hasNext()).thenReturn(false);
		ingesterKafkaConsumer.run();
		verify(singleRecordProcessor, times(0)).processRecord(anyObject());

	}

	@Test
	public void testRunWithImpressionRecord() throws IOException {
		ConsumerIterator<byte[], byte[]> it = mock(ConsumerIterator.class);
		when(stream.iterator()).thenReturn(it);
		when(it.hasNext()).thenReturn(true).thenReturn(false);
		MessageAndMetadata<byte[], byte[]> messageandmetadata = mock(MessageAndMetadata.class);
		when(it.next()).thenReturn(messageandmetadata);
		when(messageandmetadata.message()).thenReturn(EventRecordBytesFixture.ImpressionRecord(new BigInteger("3")));
		ingesterKafkaConsumer.run();
		verify(singleRecordProcessor, times(1)).processRecord(anyObject());

	}

	@Test
	public void testRunWithBidRecord() throws IOException {
		ConsumerIterator<byte[], byte[]> it = mock(ConsumerIterator.class);
		when(stream.iterator()).thenReturn(it);
		when(it.hasNext()).thenReturn(true).thenReturn(false);
		MessageAndMetadata<byte[], byte[]> messageandmetadata = mock(MessageAndMetadata.class);
		when(it.next()).thenReturn(messageandmetadata);
		when(messageandmetadata.message()).thenReturn(EventRecordBytesFixture.BidRecord(new BigInteger("3")));
		ingesterKafkaConsumer.run();
		verify(singleRecordProcessor, times(1)).processRecord(anyObject());

	}

}
