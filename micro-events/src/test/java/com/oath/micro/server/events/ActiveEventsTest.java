package com.oath.micro.server.events;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class ActiveEventsTest {

    ActiveEvents activeEvents;

    @Before
    public void setup() {
        activeEvents = new ActiveEvents();
    }

    @Test
    public void namingClasses() {
        System.out.println(ActiveEventsTest.class.getCanonicalName());
        System.out.println(ActiveEventsTest.class.getName());
        System.out.println(ActiveEventsTest.class.getSimpleName());
        System.out.println(ActiveEventsTest.class.getPackage()
                                                 .getName());
        System.out.println(ActiveEventsTest.class.getTypeName());

    }

    @Test
    public void testOneEvent() {
        activeEvents.active("hello", new BaseEventInfo());
        assertThat(activeEvents.events(), is(1));
    }

    @Test
    public void testTwoEvents() {
        activeEvents.active("hello", new BaseEventInfo());
        activeEvents.active("hello2", new BaseEventInfo());
        assertThat(activeEvents.events(), is(2));
        assertThat(activeEvents.size(), is(2));
    }

    @Test
    public void testTwoIdenticalEvents() {
        activeEvents.active("hello", new BaseEventInfo());
        activeEvents.active("hello", new BaseEventInfo());
        assertThat(activeEvents.events(), is(2));
        assertThat(activeEvents.size(), is(1));
    }

    @Test
    public void testFinishedString() {
        activeEvents.active("hello", new BaseEventInfo());
        activeEvents.finished("hello");
        assertThat(activeEvents.events(), is(1));
        assertThat(activeEvents.size(), is(0));
    }

    @Test
    public void testFinishedStringImmutableMap() {
        activeEvents.active("hello", new BaseEventInfo());
        activeEvents.finished("hello", ImmutableMap.of("hello", "world"));
        assertThat(activeEvents.events(), is(1));
        assertThat(activeEvents.size(), is(0));
        assertThat(activeEvents.toString(), containsString("world"));
    }

}
