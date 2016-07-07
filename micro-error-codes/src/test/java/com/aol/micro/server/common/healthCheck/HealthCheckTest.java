package com.aol.micro.server.common.healthCheck;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.health.ErrorEvent;
import com.aol.micro.server.health.HealthCheck;
import com.aol.micro.server.health.HealthChecker;
import com.aol.micro.server.health.HealthStatus;
import com.google.common.eventbus.EventBus;

public class HealthCheckTest {

    HealthCheck check;
    EventBus eventBus;

    @Before
    public void setup() {

        eventBus = mock(EventBus.class);

        check = new HealthCheck(
                                new HealthChecker(), 10, 20, eventBus);
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
        for (int i = 0; i < check.maxSize * 2; i++) {
            check.onEvent(new ErrorEvent());
            assertThat(check.errors.size(), is(Math.min(i + 1, check.maxSize)));
        }
    }

    @Test
    public void testCheckHealthStatusErrorsOld() throws InterruptedException {
        check.onEvent(new ErrorEvent());
        check.healthCheckHelper.timeThresholdForNormal = 1l;
        Thread.sleep(1l);

        HealthStatus status = check.checkHealthStatus();
        assertThat(status.mdmsDbConnection, is(HealthStatus.State.Not_Applicable));
        assertThat(status.aceDbConnection, is(HealthStatus.State.Not_Applicable));
        assertThat(status.generalProcessing, is(HealthStatus.State.Ok));
        assertThat(status.recentErrors.size(), is(1));
    }

}
