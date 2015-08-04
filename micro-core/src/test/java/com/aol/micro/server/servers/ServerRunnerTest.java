package com.aol.micro.server.servers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.servers.grizzly.GrizzlyApplication;
import com.aol.micro.server.servers.model.AllData;
import com.aol.micro.server.servers.model.ServerData;



public class ServerRunnerTest {

	private ServerRunner serverRunner;
	private GrizzlyApplication serverApplication1;
	private GrizzlyApplication serverApplication2;
	private ServerData[] registered;
	volatile int server1Count =0;
	volatile int server2Count =0;
	@Before
	public void setUp() {
		
		server1Count =0;
		server2Count =0;

		ServerData data1 = new ServerData(8080,Arrays.asList(), null, "url1", () -> "app-context");
		ServerData data2 = new ServerData(8081, Arrays.asList(), null, "url2", () -> "test-context");

		serverApplication1 = new GrizzlyApplication(AllData.builder().serverData(data1).build()){
			public void run(CompletableFuture start,CompletableFuture end) {
				server1Count++;
				start.complete(true);
				
			}
		};
		serverApplication2 =  new GrizzlyApplication(AllData.builder().serverData(data2).build()){
			public void run(CompletableFuture start,CompletableFuture end) {
				server2Count++;
				start.complete(true);
				
			}
		};

		serverRunner = new ServerRunner( (array) -> {registered = array;  } ,
				Arrays.asList(serverApplication1, serverApplication2), new CompletableFuture());

		
	}

	@Test
	public void testRun() {
		serverRunner.run();
		assertThat(server1Count,is(1));
		assertThat(server2Count,is(1));
	}
}
