package com.aol.micro.server.spring.metrics;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstantGaugeTest {

	@Test
	public void instantGauge() {
		InstantGauge gauge = new InstantGauge();
		gauge.increment();
		gauge.increase(3);

		assertEquals(4l ,gauge.getValue().longValue());
		assertEquals(0l ,gauge.getValue().longValue());

	}

}
