package com.aol.micro.server.reactive;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.Executors;

import javaslang.collection.List;
import javaslang.collection.Stream;

import org.junit.Test;

import com.aol.cyclops.sequence.SequenceM;
import com.aol.cyclops.sequence.reactivestreams.CyclopsSubscriber;
import com.aol.micro.server.javaslang.reactive.JavaslangReactive;
import com.aol.simple.react.stream.traits.LazyFutureStream;

public class ReactiveStreamsTest implements JavaslangReactive {

	@Test
	public void publish(){
		
		Stream<Integer> javaslangStream = this.publish(LazyFutureStream.of(1,2,3));
		
		assertThat(javaslangStream.toList(),equalTo(List.ofAll(1,2,3)));
	}
	@Test
	public void subscribe(){
		Stream<Integer> stream = Stream.ofAll(1,2,3);
		CyclopsSubscriber<Integer> sub = SequenceM.subscriber();
		this.subsribe(stream, sub);
		
		assertThat(sub.sequenceM().toList(),equalTo(Arrays.asList(1,2,3)));
	}
	@Test
	public void subscribeAsync(){
		Stream<Integer> stream = Stream.ofAll(1,2,3);
		CyclopsSubscriber<Integer> sub = SequenceM.subscriber();
		this.subsribe(stream, sub,Executors.newFixedThreadPool(1));
		
		assertThat(sub.sequenceM().toList(),equalTo(Arrays.asList(1,2,3)));
	}
}
