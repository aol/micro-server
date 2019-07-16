package com.oath.micro.server.events;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

public class RequestsBeingExecutedTest {

    RequestsBeingExecuted requests;
    EventBus bus;
    RequestTypes types;

    @Before
    public void setup() {
        bus = new EventBus();
        requests = new RequestsBeingExecuted(bus);
        types = new RequestTypes(bus, true);
        types.getMap()
             .put("default", requests);
    }

    @Test
    public void oneEvent() {
        bus.post(RequestEvents.start("data", "100"));
        assertThat(requests.events(), is(1));
    }

    @Test
    public void twoEvents() {
        bus.post(RequestEvents.start("data", "100"));
        bus.post(RequestEvents.start("data", "120"));
        assertThat(requests.events(), is(2));
    }

    @Test
    public void twoIdenticalEvents() {
        bus.post(RequestEvents.start("data", "100"));
        bus.post(RequestEvents.start("data", "100"));
        assertThat(requests.events(), is(2));
    }

    @Test
    public void oneEventSize() {
        bus.post(RequestEvents.start("data", "100"));
        assertThat(requests.size(), is(1));
    }

    @Test
    public void twoEventsSize() {
        bus.post(RequestEvents.start("data", "100"));
        bus.post(RequestEvents.start("data", "120"));
        assertThat(requests.size(), is(2));
    }

    @Test
    public void twoIdenticalEventsSize() {
        bus.post(RequestEvents.start("data", "100"));
        bus.post(RequestEvents.start("data", "100"));
        assertThat(requests.size(), is(1));
    }

    @Test
    public void twoEventsOneFinished() {
        bus.post(RequestEvents.start("data", "100"));
        bus.post(RequestEvents.start("data", "120"));
        bus.post(RequestEvents.finish("data", "120"));
        assertThat(requests.events(), is(2));
        assertThat(requests.size(), is(1));
    }

    @Test
    public void twoEventsDifferentTypesOneFinishedDefaultTypeIsIgnored() {
        requests = new RequestsBeingExecuted(
                                             bus, "typeA");

        types.getMap()
             .put("typeA", requests);
        bus.post(RequestEvents.start("data", "130"));
        bus.post(RequestEvents.start("data", "120", "typeA", "data2"));
        bus.post(RequestEvents.finish("data", "120", "typeA"));
        assertThat(requests.events(), is(1));
        assertThat(requests.size(), is(0));
    }

    @Test
    public void testToString() {
        bus.post(RequestEvents.start("data", "100"));
        bus.post(RequestEvents.start("data", "120"));
        bus.post(RequestEvents.finish("data", "120"));

        assertThat(requests.toString(), containsString("\"removed\":1"));
        assertThat(requests.toString(), containsString("\"added\":2"));

    }

}
