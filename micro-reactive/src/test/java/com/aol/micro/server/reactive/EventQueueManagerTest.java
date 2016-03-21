package com.aol.micro.server.reactive;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.aol.cyclops.control.AnyM;
import com.aol.cyclops.control.Eval;
import com.aol.cyclops.control.Maybe;
import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.control.monads.transformers.MaybeT;
import com.aol.cyclops.data.async.QueueFactories;


public class EventQueueManagerTest {
	EventQueueManager<String> manager;
	Executor ex = Executors.newFixedThreadPool(10);
	volatile String recieved;
	@Before
	public void setup(){
		recieved =null;
		manager = EventQueueManager.of(ex,QueueFactories.boundedNonBlockingQueue(1000));
	}
	

	@Test
	public void testPush() {
		manager.push("hello", "world");
	}

	@Test
	public void testConfigure() throws InterruptedException {
	
		manager.forEach("hello",a->recieved= a);
		
		manager.push("hello", "world");
		
		Thread.sleep(100);
		
		System.out.println(recieved);
		assertThat(recieved,equalTo("world"));
	}
	
	@Test
	public void testStream() throws InterruptedException {
		manager.stream("2")
		        .futureOperations(ex)
		        .forEach(a->recieved= a);
		
		manager.push("2", "world");
		
		
		Thread.sleep(100);
		
		System.out.println(recieved);
		assertThat(recieved,equalTo("world"));
	}

	@Test
	public void testLazyValue() {
		
		ReactiveSeq.generate(()->"input")
					.onePer(1,TimeUnit.SECONDS)
					.futureOperations(ex)
					.forEach(n->manager.push("lazy",n));
					
		Eval<String> lazy = manager.lazy("lazy");
		
		lazy = lazy.map(in->in+"-message!")
				   .map(in->in+"!");
		
		
		
		
		
		assertThat(lazy.get(),equalTo("input-message!!"));
		assertThat(lazy.get(),equalTo("input-message!!"));
			 
       
	}
	volatile int count =0;
	@Test
	public void testMaybe() {
		 count =0;
		ReactiveSeq.generate(()->"input")
					.onePer(1,TimeUnit.SECONDS)
					.map(s->s+":"+(count++))
					.futureOperations(ex)
					.forEach(n->manager.push("lazy",n));
					
		Maybe<String> lazy1 = manager.maybe("lazy");
		Maybe<String> lazy2 = manager.maybe("lazy");
		
		
		
		
		
		assertThat(lazy1.get(),equalTo("input:1"));
		assertThat(lazy2.get(),equalTo("input:2"));
			 
       
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
