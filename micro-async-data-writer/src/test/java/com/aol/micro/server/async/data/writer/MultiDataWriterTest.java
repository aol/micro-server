package com.aol.micro.server.async.data.writer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.aol.cyclops.data.collections.extensions.standard.ListX;

public class MultiDataWriterTest {

	MultiDataWriter<String> writer;
	MultiDataWriter<String> empty;
	private DummyDataWriter dataWriter1;
	private DummyDataWriter dataWriter2;
	@Before
	public void setup(){
		dataWriter1 = new DummyDataWriter();
		dataWriter2 = new DummyDataWriter();
		writer = new MultiDataWriter<>(ListX.of(dataWriter1,dataWriter2));
		empty = new MultiDataWriter<>(ListX.empty());
	}
	@Test
	public void bothDataWritersUpdated() {
		writer.saveAndIncrement("hello world");
		assertThat(dataWriter1.loadAndGet().get(),equalTo("hello world"));
		assertThat(dataWriter2.loadAndGet().get(),equalTo("hello world"));
	}
	@Test
	public void emptySaveAndIncrement() {
		assertThat(empty.saveAndIncrement("hello world").get(),equalTo(null));	
	}
	@Test
	public void loadAndGetReturnsDataFromFirst() {
		dataWriter1.setData("one");
		dataWriter2.setData("two");
		String data = writer.loadAndGet().get();
		assertThat(data,equalTo("one"));
	}
	@Test
	public void loadAndGetReturnsNullForEmpty() {
		String data = empty.loadAndGet().get();
		assertThat(data,equalTo(null));
	}
	@Test
	public void isOutOfDateReturnsFalseIfSecondOnlyIsFalse() {
		dataWriter1.setOutofdate(true);
		dataWriter2.setOutofdate(false);
		boolean outofdate = writer.isOutOfDate().get();
		assertThat(outofdate,equalTo(true));
	}
	@Test
	public void isOutOfDateReturnsFalseIfFirstOnlyIsFalse() {
		dataWriter1.setOutofdate(false);
		dataWriter2.setOutofdate(true);
		boolean outofdate = writer.isOutOfDate().get();
		assertThat(outofdate,equalTo(true));
	}
	@Test
	public void isOutOfDateReturnsFalseIfBothAreFalse() {
		dataWriter1.setOutofdate(false);
		dataWriter2.setOutofdate(false);
		boolean outofdate = writer.isOutOfDate().get();
		assertThat(outofdate,equalTo(false));
	}
	@Test
	public void isOutOfDateReturnsTrueIfBothAreTrue() {
		dataWriter1.setOutofdate(true);
		dataWriter2.setOutofdate(true);
		boolean outofdate = writer.isOutOfDate().get();
		assertThat(outofdate,equalTo(true));
	}
	@Test
	public void isOutofDateWorksEmpty() {
		
		boolean outofdate = empty.isOutOfDate().get();
		assertThat(outofdate,equalTo(false));
	}
}
