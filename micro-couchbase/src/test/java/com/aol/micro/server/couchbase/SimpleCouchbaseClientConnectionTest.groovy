package com.aol.micro.server.couchbase

import com.aol.micro.server.distributed.DistributedCache;

import static org.hamcrest.Matchers.is
import static org.junit.Assert.*
import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import com.couchbase.client.CouchbaseClient

class SimpleCouchbaseClientConnectionTest {

	CouchbaseClient  client
	DistributedCache con

	@Before
	public void setup() {
		client = Mockito.mock(CouchbaseClient)
		con  = new CouchbaseDistributedCacheClient(client,1,1,1)
	}
	@Test
	public void testDelete() {
		con.delete("key")
		Mockito.verify(client).delete(any(String))
	}

	@Test
	public void testGet() {
		con.get("key")
		Mockito.verify(client).get(any(String))
	}

	@Test
	public void testGetDistributedCacheDisabled() {
		con  = new CouchbaseDistributedCacheClient(null,1,1,1)
		Optional result = con.get("key")
		assertThat(result, is(Optional.empty()))
	}
}
