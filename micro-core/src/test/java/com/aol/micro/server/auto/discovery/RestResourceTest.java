package com.aol.micro.server.auto.discovery;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import lombok.Getter;

import org.junit.Test;

import com.aol.cyclops.trycatch.Try;
import com.aol.micro.server.reactive.Pipes;
import com.aol.simple.react.async.Queue;
import com.aol.simple.react.stream.traits.LazyFutureStream;
public class RestResourceTest {

	@Test
	public void testNoPipe() {
		Pipes.clear();
		assertTrue(new MyResource().queue().isFailure());
	}
	@Test
	public void testPipe() {
		LazyFutureStream<String> stream = Pipes.register("hello", new Queue<String>());
		assertTrue(new MyResource().queue().isSuccess());
		
		assertThat(stream.limit(1).toList(),equalTo(Arrays.asList("world")));
		
	}
	@Test
	public void testAsync() {
		MyResource resource = new MyResource();
		resource.async();
		
		assertThat(resource.getVal(),equalTo("HELLO"));
		
	}
	@Test
	public void testSync() {
		MyResource resource = new MyResource();
		resource.sync();
		
		assertThat(resource.getVal(),equalTo("HELLO"));
		
	}
	static class MyResource implements RestResource{
		@Getter
		String val;
		public Try<Boolean,MissingPipeException> queue(){
			return this.enqueue("hello","world");
		}
		public void async(){
			this.async(lr->lr.of("hello").map(String::toUpperCase).peek(str->val=str)).block();
		}
		public void sync(){
			this.sync(lr->lr.of("hello").map(String::toUpperCase).peek(str->val=str)).block();
		}
	}

}
