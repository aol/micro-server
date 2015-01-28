package com.aol.micro.server.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import app.servlet.com.aol.micro.server.ServletStatusResource;

import com.google.common.collect.Lists;

public class RestResourcesTest {

	@Test
	public void testConstructors() {
		assertThat(new RestResources().getAllResources().size(), is(0));
		assertThat(new RestResources(Lists.newArrayList(new ServletStatusResource())).getAllResources().size(), is(1));

	}

}

