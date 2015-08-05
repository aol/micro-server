package com.aol.micro.server.application.registry;

import static org.junit.Assert.*;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.rest.jackson.JacksonUtil;



public class RegisterEntryTest {

	RegisterEntry entry;

	@Before
	public void setUp() throws Exception {
		entry = new RegisterEntry(8080, "hostname", "name", "context", new Date());
	}

	@Test
	public void test() {
		
		assertTrue( JacksonUtil.serializeToJson(entry).contains("\"context\":\"context"));
	}
}
