package com.aol.micro.server.servers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	@Before
	public void setUp() {
		serverApplication1 = mock(GrizzlyApplication.class);
		serverApplication2 = mock(GrizzlyApplication.class);

		ServerData data1 = new ServerData(8080, Lists.newArrayList(), Lists.newArrayList(),Lists.newArrayList(), null, "url1", () -> "app-context");
		ServerData data2 = new ServerData(8081, Lists.newArrayList(),Lists.newArrayList(), Lists.newArrayList(), null, "url2", () -> "test-context");

		when(serverApplication1.getServerData()).thenReturn(data1);
		when(serverApplication2.getServerData()).thenReturn(data2);

		serverRunner = new ServerRunner( (array) -> {registered = array;  } ,
				Lists.newArrayList(serverApplication1, serverApplication2));

	}

	@Test
	public void testRun() {
		serverRunner.run(new CompletableFuture());
		verify(serverApplication1, times(1)).run();
		verify(serverApplication2, times(1)).run();
	}
}
