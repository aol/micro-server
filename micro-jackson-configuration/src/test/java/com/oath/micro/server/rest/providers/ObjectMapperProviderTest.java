package com.oath.micro.server.rest.providers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.rest.jackson.JacksonUtil;

public class ObjectMapperProviderTest {

	ObjectMapperProvider provider;
	
	@Before
	public void setup(){
		provider = new ObjectMapperProvider();
	}
	@Test
	public void testGetContext() {
		assertThat(provider.getContext(null),is(JacksonUtil.getMapper()));
	}

}

