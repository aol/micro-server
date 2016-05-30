package com.aol.micro.server.distlock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class LockControllerTest {
	
	LockController lockController;
	DistributedLockService lock;
	LockKeyProvider lockKeyProvider;
	
	@Before
	public void setup(){
		lock = mock(DistributedLockService.class);
		lockController = new LockController();
		lockController.lock = lock;
		lockKeyProvider = () -> "key";
	}
	@Test
	public void testAcquire() {
		lockController.acquire(lockKeyProvider);
		verify(lock, times(1)).tryLock("key");
	}
	
	@Test
	public void testAcquireSuccess() {
		when(lock.tryLock("key")).thenReturn(true);		
		assertThat(lockController.acquire(lockKeyProvider), is(true));		
	}

	@Test
	public void testAcquireAndLog() {
		lockController.acquireAndLog(lockKeyProvider);
		verify(lock, times(1)).tryLock("key");
	}
	
	@Test
	public void testAcquireAndLogSuccess() {
		when(lock.tryLock("key")).thenReturn(true);		
		assertThat(lockController.acquireAndLog(lockKeyProvider), is(true));		
	}

}
