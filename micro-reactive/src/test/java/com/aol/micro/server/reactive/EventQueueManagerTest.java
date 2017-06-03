package com.aol.micro.server.reactive;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import cyclops.async.QueueFactories;
import cyclops.control.Eval;
import cyclops.control.Maybe;
import cyclops.stream.ReactiveSeq;
import org.junit.Before;
import org.junit.Test;


public class EventQueueManagerTest {
	
	
	private String processJob(int in){
		return null;
	}
	EventQueueManager<String> manager;
	Executor ex = Executors.newFixedThreadPool(10);
	volatile String recieved;
	@Before
	public void setup(){
		recieved =null;
		manager = EventQueueManager.of(ex, QueueFactories.boundedNonBlockingQueue(1000));
	}
	

	@Test
	public void testPush() {
		manager.push("hello", "world");
	}

	public void handleEvent(String e){
		
	}
	@Test
	public void testConfigure() throws InterruptedException {
		String data = "";
		manager.forEach("bus-a",this::handleEvent);
		
		manager.push("bus-a", data);
		
	
		manager.forEach("hello",a->recieved= a);
		
		manager.push("hello", "world");
		
		Thread.sleep(100);
		
		System.out.println(recieved);
		assertThat(recieved,equalTo("world"));
	}
	public String process(String s){
		return null;
	}
	@Test
	public void testStream() throws InterruptedException {
		
	
		
		manager.stream("2")
				.foldFuture(ex,
		        s->s.forEach(Long.MAX_VALUE,a->recieved= a));
		
		manager.push("2", "world");
		
		
		Thread.sleep(100);
		
		System.out.println(recieved);
		assertThat(recieved,equalTo("world"));
	}

	@Test
	public void testLazyValue() {
		
		ReactiveSeq.generate(()->"input")
					.onePer(1,TimeUnit.SECONDS)
					.foldFuture(ex,
							s->s.forEach(Long.MAX_VALUE,n->manager.push("lazy",n)));
					
		Eval<String> lazy = manager.lazy("lazy");
		
		lazy = lazy.map(in->in+"-message!")
				   .map(in->in+"!");
		
		
		
		
		
		assertThat(lazy.get(),equalTo("input-message!!"));
		assertThat(lazy.get(),equalTo("input-message!!"));
			 
       
	}
	AtomicInteger  count =new AtomicInteger(0);
	@Test
	public void testMaybe() {
		
		ReactiveSeq.generate(()->"input")
					.onePer(1,TimeUnit.SECONDS)
					.map(s->s+":"+count.incrementAndGet())
					.peek(System.out::println)
					.foldFuture(ex,
					 s->s.forEach(Long.MAX_VALUE,n->manager.push("lazy",n)));
					
		Maybe<String> lazy1 = manager.maybe("lazy");
		Maybe<String> lazy2 = manager.maybe("lazy");
		
		
		
		
		
		assertThat(lazy1.get(),anyOf(equalTo("input:1"),equalTo("input:2")));
		assertThat(lazy2.get(),anyOf(equalTo("input:1"),equalTo("input:2"),equalTo("input:3")));
			 
       
	}
	
	public String restCall(String in){
		return "hello";
	}

	@Test
	public void testIoFutureStream() throws InterruptedException {
		
		
		
		
		manager.ioFutureStream("futureStream")
        	   .peek(a->recieved= a)
        	   .run();

		
		manager.push("futureStream", "world");

		Thread.sleep(100);

		System.out.println(recieved);
		assertThat(recieved,equalTo("world"));
	}

}
