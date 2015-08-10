package com.aol.micro.server.couchbase.distributed.locking;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;

import net.spy.memcached.CASValue;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.ops.OperationStatus;

import org.junit.Before;
import org.junit.Test;

import com.couchbase.client.CouchbaseClient;

public class DistributedLockServiceCouchbaseImplTest {

	private DistributedLockServiceCouchbaseImpl impl;
	private CouchbaseClient couchbaseClient;
	private OperationFuture<Boolean> res;
	private OperationStatus operationStatus;
	private CASValue<Object> casObj;

	@Before
	public void setUp() {
		couchbaseClient = mock(CouchbaseClient.class);
		impl = new DistributedLockServiceCouchbaseImpl(1800);
		impl.setCouchbaseClient(couchbaseClient);
		res = mock(OperationFuture.class);
		casObj = mock(CASValue.class);
		operationStatus = mock(OperationStatus.class);
	}

	@Test
	public void testNoKeyInCouchBaseAndGetLock() throws InterruptedException, ExecutionException {
		canAddKeyMock();
		assertThat(impl.tryLock("key1"), is(true));
	}

	@Test
	public void testKeyInCouchBaseAndNotGetLock() throws InterruptedException, ExecutionException {
		canNotAddKeyMock();
		assertThat(impl.tryLock("key1"), is(false));
	}

	@Test
	public void hasLockAlready() throws InterruptedException, ExecutionException {
		canAddKeyMock();
		assertThat(impl.tryLock("key1"), is(true));

		canNotAddKeyMock();
		stillTheSameValueMock();
		assertThat(impl.tryLock("key1"), is(true));

	}

	@Test
	public void hasLockAlreadyButLostTheLockAndThenGetTheLockBack() throws InterruptedException, ExecutionException {
		canAddKeyMock();
		assertThat(impl.tryLock("key1"), is(true));

		canNotAddKeyMock();
		notTheSameValueMock();
		assertThat(impl.tryLock("key1"), is(false));

		canNotAddKeyMock();
		assertThat(impl.tryLock("key1"), is(false));

		canAddKeyMock();
		assertThat(impl.tryLock("key1"), is(true));

	}

	@Test
	public void canNotRenewLock() throws InterruptedException, ExecutionException {
		canAddKeyMock();
		assertThat(impl.tryLock("key1"), is(true));

		canNotAddKeyMock();
		notTheSameValueMock();

		impl.renewLock();
		assertThat(impl.getLockDataMap().get("key1").isHasLock(), is(false));
	}

	@Test
	public void canRenewLock() throws InterruptedException, ExecutionException {
		canAddKeyMock();
		assertThat(impl.tryLock("key1"), is(true));

		canNotAddKeyMock();
		stillTheSameValueMock();

		impl.renewLock();
		assertThat(impl.getLockDataMap().get("key1").isHasLock(), is(true));
	}

	@Test
	public void testTryReleaseLockKeyNotInMemory() throws InterruptedException, ExecutionException {
		assertThat(impl.tryReleaseLock("key1"), is(false));
	}

	@Test
	public void testTryReleaseLock() throws InterruptedException, ExecutionException {
		canAddKeyMock();
		assertThat(impl.tryLock("key1"), is(true));

		stillTheSameValueMock();
		successfulDeleteKeyMock();

		assertThat(impl.tryReleaseLock("key1"), is(true));
		assertThat(impl.getLockDataMap().get("key1").isHasLock(), is(false));
	}

	@Test
	public void testTryReleaseLockUnsuccessful() throws InterruptedException, ExecutionException {
		canAddKeyMock();
		assertThat(impl.tryLock("key1"), is(true));

		stillTheSameValueMock();
		unsuccessfulDeleteKeyMock();

		assertThat(impl.tryReleaseLock("key1"), is(false));
		assertThat(impl.getLockDataMap().get("key1").isHasLock(), is(false));
	}

	@Test
	public void testTryReleaseLockValueNotSame() throws InterruptedException, ExecutionException {
		canAddKeyMock();
		assertThat(impl.tryLock("key1"), is(true));

		notTheSameValueMock();

		assertThat(impl.tryReleaseLock("key1"), is(false));
		assertThat(impl.getLockDataMap().get("key1").isHasLock(), is(false));
	}

	@Test
	public void testCanAddKeyException() {
		when(couchbaseClient.add(anyString(), anyInt(), anyObject())).thenThrow(new IllegalStateException());
		assertThat(impl.tryLock("key1"), is(false));
	}

	@Test
	public void testStillTheSameValueException() throws InterruptedException, ExecutionException {
		canAddKeyMock();
		assertThat(impl.tryLock("key1"), is(true));
		when(couchbaseClient.getAndTouch(anyString(), anyInt())).thenThrow(new IllegalStateException());
		canNotAddKeyMock();
		assertThat(impl.tryLock("key1"), is(false));
	}

	private void successfulDeleteKeyMock() {
		when(couchbaseClient.delete("key1")).thenReturn(res);
		when(res.getStatus()).thenReturn(operationStatus);
		when(operationStatus.isSuccess()).thenReturn(true);
	}

	private void unsuccessfulDeleteKeyMock() {
		when(couchbaseClient.delete("key1")).thenReturn(res);
		when(res.getStatus()).thenReturn(operationStatus);
		when(operationStatus.isSuccess()).thenReturn(false);
	}

	private void canAddKeyMock() throws InterruptedException, ExecutionException {
		when(couchbaseClient.add(anyString(), anyInt(), anyObject())).thenReturn(res);
		when(res.get()).thenReturn(true);
	}

	private void canNotAddKeyMock() throws InterruptedException, ExecutionException {
		when(couchbaseClient.add(anyString(), anyInt(), anyObject())).thenReturn(res);
		when(res.get()).thenReturn(false);
	}

	private void stillTheSameValueMock() {
		when(couchbaseClient.getAndTouch(anyString(), anyInt())).thenReturn(casObj);
		when(casObj.getValue()).thenReturn(impl.getLockDataMap().get("key1").getValue());
	}

	private void notTheSameValueMock() {
		when(couchbaseClient.getAndTouch(anyString(), anyInt())).thenReturn(casObj);
		when(casObj.getValue()).thenReturn("");
	}
}
