package com.aol.micro.server.common.exceptions;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.errors.ErrorBus;
import com.aol.micro.server.errors.InvalidStateException;
import com.aol.micro.server.health.ErrorEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import app.errors.com.aol.micro.server.Errors;

public class InvalidStateExceptionTest {

    boolean eventTriggered = false;

    ErrorBus bus;

    @Before
    public void setup() {
        bus = new ErrorBus();
        EventBus ebus = new EventBus();
        ebus.register(this);
        ErrorBus.setErrorBus(ebus);
    }

    @Test
    public void testEventErrorSuscriber() {
        new InvalidStateException(
                                  Errors.QUERY_FAILURE.format());
        assertTrue(eventTriggered);
    }

    @Subscribe
    public void onEvent(final ErrorEvent event) {
        eventTriggered = true;
    }
}
