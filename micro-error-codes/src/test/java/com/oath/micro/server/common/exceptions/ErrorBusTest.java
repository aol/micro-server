package com.oath.micro.server.common.exceptions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.oath.micro.server.errors.ErrorBus;
import com.google.common.eventbus.EventBus;

public class ErrorBusTest {

    ErrorBus errorBus = new ErrorBus();

    @Test
    public void testNotSet() {
        errorBus.post(this);
    }

    @Test
    public void testSet() {
        EventBus bus = mock(EventBus.class);
        ErrorBus.setErrorBus(bus);
        errorBus.post(this);
        verify(bus).post(this);
    }

}
