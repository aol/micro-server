package com.oath.micro.server.events;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ConcurrentHashMultiset;

public class LoggingRateLimiterTest {

	LoggingRateLimiter rateLimiter;
	
	@Before
	public void setup(){
		rateLimiter = new  LoggingRateLimiter();
		rateLimiter.resetAfterLimit() ;
	}
	
	@Test
	public void testAddAndEnsureFrequency() {
		for(int i=0;i<10;i++){
		 rateLimiter.addAndEnsureFrequency(this);
		}
		rateLimiter.capacityAvailable(this, 5, () ->fail ("failure"));
		
	}

	@Test
	public void testDoesNotReset(){
		ConcurrentHashMultiset freq = rateLimiter.getFrequency();
		rateLimiter.resetAfterLimit();
		assertThat (freq,is( rateLimiter.getFrequency()));
	}
	
	@Test
	public void testResetAfterLimit() throws InterruptedException {
		rateLimiter = new  LoggingRateLimiter(0);
		ConcurrentHashMultiset freq = rateLimiter.getFrequency();
		
		rateLimiter.resetAfterLimit();
		
		assertTrue (freq != rateLimiter.getFrequency() );
		freq = rateLimiter.getFrequency();
		Thread.sleep(1);
		
		rateLimiter.resetAfterLimit();
		assertThat (freq,is( rateLimiter.getFrequency()));
	}
	boolean ran= false;
	@Test
	public void testCapacityAvailable() {
		ran = false;
		rateLimiter.capacityAvailable(this, 100, () -> { ran = true;});
		
		assertTrue( ran);
	}

}
