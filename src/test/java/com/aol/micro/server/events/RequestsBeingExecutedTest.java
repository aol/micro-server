package com.aol.micro.server.events;

import static org.junit.Assert.fail;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

public class RequestsBeingExecutedTest {

	RequestsBeingExecuted requests;
	EventBus bus;
	@Before
	public void setup(){
		bus = new EventBus();
		requests = new RequestsBeingExecuted(bus,true);
	}
	@Test
	public void oneEvent() {
		bus.post(RequestsBeingExecuted.start("data", 100l));
		assertThat(requests.events(),is(1));
	}
	@Test
	public void twoEvents() {
		bus.post(RequestsBeingExecuted.start("data", 100l));
		bus.post(RequestsBeingExecuted.start("data", 120l));
		assertThat(requests.events(),is(2));
	}
	@Test
	public void twoIdenticalEvents() {
		bus.post(RequestsBeingExecuted.start("data", 100l));
		bus.post(RequestsBeingExecuted.start("data", 100l));
		assertThat(requests.events(),is(2));
	}

	@Test
	public void oneEventSize() {
		bus.post(RequestsBeingExecuted.start("data", 100l));
		assertThat(requests.size(),is(1));
	}
	@Test
	public void twoEventsSize() {
		bus.post(RequestsBeingExecuted.start("data", 100l));
		bus.post(RequestsBeingExecuted.start("data", 120l));
		assertThat(requests.size(),is(2));
	}
	@Test
	public void twoIdenticalEventsSize() {
		bus.post(RequestsBeingExecuted.start("data", 100l));
		bus.post(RequestsBeingExecuted.start("data", 100l));
		assertThat(requests.size(),is(1));
	}
	@Test
	public void twoEventsOneFinished() {
		bus.post(RequestsBeingExecuted.start("data", 100l));
		bus.post(RequestsBeingExecuted.start("data", 120l));
		bus.post(RequestsBeingExecuted.finish("data",120l));
		assertThat(requests.events(),is(2));
		assertThat(requests.size(),is(1));
	}
	@Test
	public void testToString() {
		bus.post(RequestsBeingExecuted.start("data", 100l));
		bus.post(RequestsBeingExecuted.start("data", 120l));
		bus.post(RequestsBeingExecuted.finish("data",120l));
		
		System.out.println(requests.toString());
		assertThat(requests.toString(),containsString("\"removed\":1"));
		assertThat(requests.toString(),containsString("\"added\":2"));
		
	}

	

}
