package com.aol.micro.server.async.data.writer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import com.aol.cyclops.control.FutureW;
import com.aol.micro.server.events.SystemData;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class AsyncDataWriterTest {

	AsyncDataWriter<String> writer;
	Executor ex = Executors.newFixedThreadPool(5);
	EventBus bus;
	private DummyManifestComparator<String> dummyMc;
	AtomicInteger eventRecieved;

	@Before
	public void setup() {
		eventRecieved = new AtomicInteger(
											0);
		bus = new EventBus();
		bus.register(this);
		dummyMc = new DummyManifestComparator<>();
		writer = new AsyncDataWriter<>(
										ex, dummyMc, bus);
	}

	@Subscribe
	public void event(SystemData data) {
		eventRecieved.incrementAndGet();
	}

	@Test
	public void testLoadAndGet() {
		assertThat(eventRecieved.get(), equalTo(0));
		dummyMc.setData("hello world");
		FutureW<String> res = writer.loadAndGet();

		assertThat(res.get(), equalTo("hello world"));
		assertThat(dummyMc.loadCalled.get(), equalTo(1));
		assertThat(eventRecieved.get(), equalTo(1));
	}

	@Test
	public void testSaveAndIncrement() {
		assertThat(eventRecieved.get(), equalTo(0));
		writer.saveAndIncrement("boo!");
		FutureW<String> res = writer.loadAndGet();
		assertThat(res.get(), equalTo("boo!"));
		assertThat(eventRecieved.get(), equalTo(2));
	}

	@Test
	public void testIsOutOfDate() {
		writer	.isOutOfDate()
				.get();
		assertThat(dummyMc.outofDateCalled.get(), equalTo(1));
	}

}
