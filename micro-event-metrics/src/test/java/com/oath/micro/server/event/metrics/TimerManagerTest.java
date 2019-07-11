package com.oath.micro.server.event.metrics;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.codahale.metrics.Timer.Context;

public class TimerManagerTest {

    TimerManager manager;

    @Before
    public void setup() {
        manager = new TimerManager(
                                   3, 5);
    }

    @Test
    public void whenValueNotPresentNoError() {
        manager.complete("1");
    }

    @Test
    public void whenValueAddedAndRemovedStopCalled() {
        Context c = Mockito.mock(Context.class);
        manager.start("1", c);
        manager.complete("1");
        Mockito.verify(c, Mockito.times(1))
               .stop();

    }

    @Test
    public void when4ValuesAddedFirstIsDropped() {

    }

}
