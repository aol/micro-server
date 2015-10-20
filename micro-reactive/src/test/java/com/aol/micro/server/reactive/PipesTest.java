package com.aol.micro.server.reactive;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.aol.simple.react.async.Queue;
import com.aol.simple.react.stream.traits.LazyFutureStream;

public class PipesTest {
	@Before
	public void setup() {
		Pipes.clear();
	}
	@Test
	public void testGetAbsent() {
		Pipes.clear();
		assertFalse(Pipes.get("hello").isPresent());
	}
	@Test
	public void testGetPresent() {
		Pipes.register("hello", new Queue());
		assertTrue(Pipes.get("hello").isPresent());
	}

	@Test
	public void testStream() {
		Queue queue = new Queue();
		queue.add("world");
		Pipes.register("hello",queue);
		assertThat(Pipes.stream("hello").limit(1).toList(),equalTo(Arrays.asList("world")));
	}
	@Test
	public void testStreamSequential() {
		Queue queue = new Queue();
		queue.add("world");
		Pipes.register("hello",queue);
		assertThat(Pipes.stream("hello").limit(1).toList(),equalTo(Arrays.asList("world")));
	}
	@Test
	public void testStreamIO() {
		Queue queue = new Queue();
		queue.add("world");
		Pipes.register("hello",queue);
		assertThat(Pipes.futureStreamIOBound("hello").limit(1).toList(),equalTo(Arrays.asList("world")));
	}
	@Test
	public void testStreamCPU() {
		Queue queue = new Queue();
		queue.add("world");
		Pipes.register("hello",queue);
		assertThat(Pipes.futureStreamCPUBound("hello").limit(1).toList(),equalTo(Arrays.asList("world")));
	}
	@Test
	public void cpuBound() {
		Queue queue = new Queue();
		LazyFutureStream<String> stream = Pipes.registerForCPU("hello", queue);
		queue.add("world");
		assertTrue(Pipes.get("hello").isPresent());
		assertThat(stream.limit(1).toList(),equalTo(Arrays.asList("world")));
	}
	@Test
	public void ioBound() {
		Queue queue = new Queue();
		LazyFutureStream<String> stream = Pipes.registerForIO("hello", queue);
		queue.add("world");
		assertTrue(Pipes.get("hello").isPresent());
		assertThat(stream.limit(1).toList(),equalTo(Arrays.asList("world")));
	}
}
