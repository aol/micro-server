package com.aol.micro.server.async.data.writer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import com.aol.cyclops.control.FutureW;

public class AsyncDataWriterTest {

	AsyncDataWriter<String> writer;
	Executor ex = Executors.newFixedThreadPool(5);
	private DummyManifestComparator<String> dummyMc;
	@Before
	public void setup(){
		dummyMc = new DummyManifestComparator<>();
		writer = new AsyncDataWriter<>(ex,dummyMc);
	}
	@Test
	public void testLoadAndGet() {
		dummyMc.setData("hello world");
		FutureW<String> res = writer.loadAndGet();
		assertThat(res.get(),equalTo("hello world"));
		assertThat(dummyMc.loadCalled.get(),equalTo(1));
	}

	@Test
	public void testSaveAndIncrement() {
		writer.saveAndIncrement("boo!");
		FutureW<String> res = writer.loadAndGet();
		assertThat(res.get(),equalTo("boo!"));
	}

	@Test
	public void testIsOutOfDate() {
		writer.isOutOfDate().get();
		assertThat(dummyMc.outofDateCalled.get(),equalTo(1));
	}

}
