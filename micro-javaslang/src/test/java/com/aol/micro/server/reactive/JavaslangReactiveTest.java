package com.aol.micro.server.reactive;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javaslang.control.Try;
import lombok.Getter;

import org.junit.Test;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.javaslang.reactive.JavaslangPipes;
import com.aol.micro.server.javaslang.reactive.JavaslangReactive;
import com.aol.simple.react.async.Queue;
import com.aol.simple.react.stream.traits.LazyFutureStream;
public class JavaslangReactiveTest {

	@Test
	public void testNoPipe() {
		JavaslangPipes.clear();
		assertTrue(new MyResource().queue().isFailure());
	}
	@Test
	public void testPipe() {
		JavaslangPipes.register("hello", new Queue<String>());
		LazyFutureStream<String> stream = JavaslangPipes.futureStreamIOBound("hello");
		assertTrue(new MyResource().queue().isSuccess());
		
		assertThat(stream.peek(System.out::println).limit(1).peek(System.out::println).toList(),equalTo(Arrays.asList("world")));
		
	}
	@Test
	public void testCPUStreamIsInSyncMode() {
		
		MyResource resource = new MyResource();
		LazyFutureStream<String> lfs = resource.asyncCPUStream();
		
		assertTrue(lfs.isAsync());
		
	}
	@Test
	public void testIOStreamIsInSyncMode() {
		
		MyResource resource = new MyResource();
		LazyFutureStream<String> lfs = resource.asyncIOStream();
		
		assertTrue(lfs.isAsync());
		
	}
	@Test
	public void testFanoutAcrossThreadsIOBound() {
		
		MyResource resource = new MyResource();
		Set<Long> threadIds = resource.asyncIOFanout();
		
		assertTrue(threadIds.size()>0);
		
	}
	
	@Test
	public void testFanoutAcrossThreadsCPUBound() {
		if(Runtime.getRuntime().availableProcessors()>1)
			System.out.println("need at least 2 threads for this test");
		MyResource resource = new MyResource();
		Set<Long> threadIds = resource.asyncCPUFanout();
		
		assertTrue(threadIds.size()>0);
		
	}
	@Test
	public void testAsyncIO() {
		MyResource resource = new MyResource();
		resource.asyncIO();
		
		assertThat(resource.getVal(),equalTo("HELLO"));
		
	}
	@Test
	public void testAsyncCPU() {
		MyResource resource = new MyResource();
		resource.asyncIO();
		
		assertThat(resource.getVal(),equalTo("HELLO"));
		
	}
	
	static class MyResource implements RestResource,JavaslangReactive{
		@Getter
		String val;
		
		
		public Try<Boolean> queue(){
			return this.enqueue("hello","world");
		}
		public LazyFutureStream<String> asyncIOStream(){
			List<String> collection = new ArrayList<>();
			for(int i=0;i<1000;i++)
				collection.add("hello");
			return this.ioStreamBuilder().from(collection).map(String::toUpperCase);
		}
		public LazyFutureStream<String> asyncCPUStream(){
			List<String> collection = new ArrayList<>();
			for(int i=0;i<1000;i++)
				collection.add("hello");
			return this.cpuStreamBuilder().from(collection).map(String::toUpperCase);
		}
		public Set<Long> asyncIOFanout(){
			List<String> collection = new ArrayList<>();
			for(int i=0;i<1000;i++)
				collection.add("hello");
			return this.ioStreamBuilder().from(collection).map(str-> Thread.currentThread().getId()).toSet();
		}
		public Set<Long> asyncCPUFanout(){
			List<String> collection = new ArrayList<>();
			for(int i=0;i<1000;i++)
				collection.add("hello");
			return this.cpuStreamBuilder().from(collection).map(str-> Thread.currentThread().getId()).toSet();
		}
		public void asyncIO(){
			this.ioStreamBuilder().of("hello").map(String::toUpperCase).peek(str->val=str).block();
		}
		public void asyncCPU(){
			this.cpuStreamBuilder().of("hello").map(String::toUpperCase).peek(str->val=str).block();
		}
		
	}

}
