package com.oath.micro.server.spring.metrics;

import java.util.concurrent.atomic.AtomicLong;

import com.codahale.metrics.Gauge;


public class InstantGauge implements Gauge<Long> {

	private final AtomicLong counter = new AtomicLong(0l);

	@Override
	public Long getValue() {
		return counter.getAndSet(0l);
		}

	public void increment() {
		counter.incrementAndGet();
	}

	public void increase(long value) {
		counter.addAndGet(value);
	}
}
