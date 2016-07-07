package com.aol.micro.server.health;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

public class HealthCheckTest {

    HealthCheck check;
    EventBus eventBus;

    @Before
    public void setup() {

        eventBus = mock(EventBus.class);

        check = new HealthCheck(
                                new HealthChecker(
                                                  10000l),
                                10, 20, eventBus);
    }

    @Test
    public void testRegister() {
        check.register();
        verify(eventBus).register(check);
    }

    @Test
    public void testOnEvent() {
        check.onEvent(new ErrorEvent());
        assertThat(check.errors.size(), is(1));
    }

    @Test
    public void testOnEventMaxNotExceeded() {
        for (int i = 0; i < check.getMaxSize() * 2; i++) {
            check.onEvent(new ErrorEvent());
            assertThat(check.errors.size(), is(Math.min(i + 1, check.getMaxSize())));
        }
    }

    @Test
    public void testCheckHealthStatusErrorsOld() throws InterruptedException {
        check = new HealthCheck(
                                new HealthChecker(
                                                  1l),
                                10, 20, eventBus);
        check.onEvent(new ErrorEvent());

        Thread.sleep(1l);

        HealthStatus status = check.checkHealthStatus();

        assertThat(status.getGeneralProcessing(), is(HealthStatus.State.Ok));
        assertThat(status.getRecentErrors()
                         .size(),
                   is(1));
    }

    @Test
    public void testSetMaxSizeTooBig() {
        check.setMaxSize(100);
        assertThat(check.getMaxSize(), is(10));
    }

    @Test
    public void testSetMaxSizeOk() {
        check.setMaxSize(15);
        assertThat(check.getMaxSize(), is(15));
    }

}
