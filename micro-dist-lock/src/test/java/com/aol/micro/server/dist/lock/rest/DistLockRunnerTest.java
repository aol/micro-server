package com.aol.micro.server.dist.lock.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class DistLockRunnerTest {

	RestAgent rest = new RestAgent();
	MicroserverApp server;

	@Before
	public void startServer() {
		server = new MicroserverApp(() -> "dist-lock");
		server.start();
	}

	@After
	public void stopServer() {
		server.stop();
	}

	@Test
	public void testIsMainProcess() {
		assertThat(rest.getJson("http://localhost:8080/dist-lock/dist/lock/own/lock"), is("true"));
	}
	
	@Test
	public void testIsMainProcessWithKey() {
		assertThat(rest.getJson("http://localhost:8080/dist-lock/dist/lock/own/lock/with/keykey2"), is("true"));
		assertThat(rest.getJson("http://localhost:8080/dist-lock/dist/lock/own/lock/with/key"), is("false"));
	}
	

}
