package com.aol.micro.server.reactive;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.aol.simple.react.async.Queue;

public class PipesTest {

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

}
