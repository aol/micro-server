package com.aol.micro.server.reactive;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.micro.server.javaslang.reactive.JavaslangSubscriber;

import javaslang.collection.Array;
import javaslang.collection.List;
import javaslang.collection.Stream;

public class JavaslangSubscriberTest {
	@Test
	public void streamTest() {
		JavaslangSubscriber<Integer> sub = JavaslangSubscriber.subscriber();
		ReactiveSeq.of(1, 2, 3).subscribe(sub);

		Stream<Integer> maybe = sub.stream();
		assertThat(maybe.toJavaList(), equalTo(Arrays.asList(1,2,3)));
	}
	@Test
	public void listTest() {
		JavaslangSubscriber<Integer> sub = JavaslangSubscriber.subscriber();
		ReactiveSeq.of(1, 2, 3).subscribe(sub);

		List<Integer> maybe = sub.list();
		assertThat(maybe.toJavaList(), equalTo(Arrays.asList(1,2,3)));
	}
	@Test
	public void arrayTest() {
		JavaslangSubscriber<Integer> sub = JavaslangSubscriber.subscriber();
		ReactiveSeq.of(1, 2, 3).subscribe(sub);

		Array<Integer> maybe = sub.array();
		assertThat(maybe.toJavaList(), equalTo(Arrays.asList(1,2,3)));
	}

}
