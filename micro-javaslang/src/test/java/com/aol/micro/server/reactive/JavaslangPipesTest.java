package com.aol.micro.server.reactive;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javaslang.collection.List;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.javaslang.reactive.JavaslangPipes;
import com.aol.simple.react.async.Queue;
import com.aol.simple.react.stream.traits.LazyFutureStream;

public class JavaslangPipesTest {
	@Before
	public void setup() {
		JavaslangPipes.clear();
	}
	@Test
	public void testGetAbsent() {
		JavaslangPipes.clear();
		assertFalse(JavaslangPipes.get("hello").isPresent());
	}
	@Test
	public void testGetPresent() {
		JavaslangPipes.register("hello", new Queue());
		assertTrue(JavaslangPipes.get("hello").isPresent());
	}

	@Test
	public void testStream() {
		Queue queue = new Queue();
		queue.add("world");
		JavaslangPipes.register("hello",queue);
		queue.close();
		assertThat(JavaslangPipes.stream("hello").take(1).toList(),equalTo(List.of("world")));
	}
	@Test
	public void testStreamSequential() {
		Queue queue = new Queue();
		queue.add("world");
		
		JavaslangPipes.register("hello",queue);
		queue.close();
		assertThat(JavaslangPipes.stream("hello").toList(),equalTo(List.of("world")));
	}
	@Test
	public void testStreamIO() {
		Queue queue = new Queue();
		queue.add("world");
		JavaslangPipes.register("hello",queue);
		assertThat(JavaslangPipes.futureStreamIOBound("hello").limit(1).toList(),equalTo(Arrays.asList("world")));
	}
	@Test
	public void testStreamCPU() {
		Queue queue = new Queue();
		queue.add("world");
		JavaslangPipes.register("hello",queue);
		assertThat(JavaslangPipes.futureStreamCPUBound("hello").limit(1).toList(),equalTo(Arrays.asList("world")));
	}
	@Test
	public void cpuBound() {
		Queue queue = new Queue();
		LazyFutureStream<String> stream = JavaslangPipes.registerForCPU("hello", queue);
		queue.add("world");
		assertTrue(JavaslangPipes.get("hello").isPresent());
		assertThat(stream.limit(1).toList(),equalTo(Arrays.asList("world")));
	}
	@Test
	public void ioBound() {
		Queue queue = new Queue();
		LazyFutureStream<String> stream = JavaslangPipes.registerForIO("hello", queue);
		queue.add("world");
		assertTrue(JavaslangPipes.get("hello").isPresent());
		assertThat(stream.limit(1).toList(),equalTo(Arrays.asList("world")));
	}
}
