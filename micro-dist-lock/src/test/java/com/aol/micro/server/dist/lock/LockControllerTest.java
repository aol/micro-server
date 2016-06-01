package com.aol.micro.server.dist.lock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class LockControllerTest {
	
	LockController controller = new LockController();	
	
	@Test
	public void testAcquireNothingInitialized() {
		assertThat(controller.acquire(), is(false));
	}
	
	@Test
	public void testAcquireOnlyLockInitialized() {
		controller.lock = mock(DistributedLockService.class);
		when(controller.lock.tryLock(anyString())).thenReturn(true);
		assertThat(controller.acquire(), is(false));
	}
	
	@Test
	public void testAcquireOnlyLock() {
		controller.lock = mock(DistributedLockService.class);
		List<LockKeyProvider> providers = new ArrayList<LockKeyProvider>();
		providers.add(()->"key1");
		providers.add(()->"key2");
		controller.lockKeyProviders = providers;
		controller.init();
		
		when(controller.lock.tryLock("key1key2")).thenReturn(true);
		assertThat(controller.acquire(), is(true));
		
		when(controller.lock.tryLock("key1key2")).thenReturn(false);
		assertThat(controller.acquire(), is(false));
		
	}
	

}
