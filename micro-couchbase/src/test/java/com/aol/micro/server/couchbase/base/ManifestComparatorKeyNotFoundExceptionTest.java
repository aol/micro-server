package com.aol.micro.server.couchbase.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.aol.micro.server.manifest.ManifestComparatorKeyNotFoundException;

public class ManifestComparatorKeyNotFoundExceptionTest {

	@Test
	public void testConstructor() {
		ManifestComparatorKeyNotFoundException exception = new ManifestComparatorKeyNotFoundException("hello");
		assertThat(exception.getMessage(), is("hello"));
	}

}
