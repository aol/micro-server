package com.oath.micro.server.events;

import com.google.common.eventbus.EventBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GenericEventTest {

    @Mock
    EventBus bus;

    @Test
    public void trigger() throws Exception {
        GenericEvent<Object> event = GenericEvent.trigger("some-event", bus);
        Mockito.verify(bus).post(event);
    }

    @Test
    public void trigger1() throws Exception {
        GenericEvent<Object> event = GenericEvent.trigger("some-event", bus, new String[] {"start", "phase1"});
        Mockito.verify(bus).post(event);
    }

    @Test
    public void trigger2() throws Exception {
        GenericEvent<String> event = GenericEvent.trigger("some-event", bus, "data", new String[]{"finish", "terminate"});
        Mockito.verify(bus).post(event);
    }

    @Test
    public void getData() throws Exception {
        GenericEvent<Integer> event = GenericEvent.trigger("some-event", bus, 10, new String[]{"finish", "terminate"});
        GenericEvent.GenericEventData<Integer> eventData = event.getData();
        assertEquals("some-event", eventData.getName());
        assertEquals(Integer.valueOf(10), eventData.getData());
        assertNotNull(eventData.getSubTypes());
        assertEquals(eventData.getSubTypes()[0], "finish");
        assertEquals(eventData.getSubTypes()[1], "terminate");
    }

}