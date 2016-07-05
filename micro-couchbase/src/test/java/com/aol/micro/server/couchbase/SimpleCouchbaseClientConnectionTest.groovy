package com.aol.micro.server.couchbase;

import static org.hamcrest.Matchers.is
import static org.junit.Assert.*
import static org.mockito.Mockito.*

import org.junit.Before import org.junit.Test import org.mockito.Mockito

import com.couchbase.client.CouchbaseClient

class SimpleCouchbaseClientConnectionTest {

	CouchbaseClient  client DistributedMapClient con

	@Before
	public void setup() {
		client = Mockito.mock(CouchbaseClient)
		con  = new DistributedMapClient(client)
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
		con  = new DistributedMapClient(null)
		Optional result = con.get("key")
		assertThat(result, is(Optional.empty()))
	}
}
