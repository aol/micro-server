package com.aol.micro.server.servers;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.servers.grizzly.GrizzlyApplication;
import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.Lists;



public class ServerRunnerTest {

	private ServerRunner serverRunner;
	private GrizzlyApplication serverApplication1;
	private GrizzlyApplication serverApplication2;
	private ServerData[] registered;
	int server1Count =0;
	int server2Count =0;
	@Before
	public void setUp() {
		
		server1Count =0;
		server2Count =0;

		ServerData data1 = new ServerData(8080, Lists.newArrayList(), Lists.newArrayList(),Lists.newArrayList(), null, "url1", () -> "app-context");
		ServerData data2 = new ServerData(8081, Lists.newArrayList(),Lists.newArrayList(), Lists.newArrayList(), null, "url2", () -> "test-context");

		serverApplication1 = new GrizzlyApplication(data1){
			public void run(CompletableFuture start,CompletableFuture end) {
				start.complete(true);
				server1Count++;
			}
		};
		serverApplication2 =  new GrizzlyApplication(data2){
			public void run(CompletableFuture start,CompletableFuture end) {
				start.complete(true);
				server2Count++;
			}
		};

		serverRunner = new ServerRunner( (array) -> {registered = array;  } ,
				Lists.newArrayList(serverApplication1, serverApplication2), new CompletableFuture());

		
	}

	@Test
	public void testRun() {
		serverRunner.run();
		assertThat(server1Count,is(1));
		assertThat(server2Count,is(1));
	}
}
