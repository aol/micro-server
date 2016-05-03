package com.aol.micro.server.reactive;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.aol.cyclops.types.futurestream.LazyFutureStream;
import com.aol.micro.server.javaslang.reactive.JavaslangReactive;
import com.aol.micro.server.javaslang.reactive.JavaslangSubscriber;

import javaslang.collection.List;
import javaslang.collection.Stream;

public class ReactiveStreamsTest   {
	
	@Test
	public void publish(){
		
		Stream<Integer> javaslangStream = JavaslangReactive.publishStream(LazyFutureStream.of(1,2,3));
		
		assertThat(javaslangStream.toList(),equalTo(List.of(1,2,3)));
	}
	@Test
	public void subscribe(){
		Stream<Integer> stream = Stream.of(1,2,3);
		JavaslangSubscriber<Integer> sub = JavaslangSubscriber.subscriber();
		JavaslangReactive.subsribeToTraversable(stream, sub);
		
		assertThat(sub.list().toJavaList(),equalTo(Arrays.asList(1,2,3)));
	}
	
}
