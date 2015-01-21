package com.aol.micro.server.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import app.com.aol.micro.server.StatusResource;

import com.google.common.collect.Lists;

public class RestResourcesTest {

	@Test
	public void testConstructors() {
		assertThat(new RestResources().getAllResources().size(), is(0));
		assertThat(new RestResources(Lists.newArrayList(new StatusResource())).getAllResources().size(), is(1));

	}

}

