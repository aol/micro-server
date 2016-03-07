package app.reactive.pipes.com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.cyclops.data.async.Queue;
import com.aol.cyclops.types.futurestream.LazyFutureStream;
import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.reactive.Pipes;
import com.aol.micro.server.testing.RestAgent;

@Microserver
public class PipesRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	LazyFutureStream<String> stream;
	@Before
	public void startServer(){
		Pipes.register("test", new Queue<String>());
		stream =  Pipes.futureStreamCPUBound("test");
		server = new MicroserverApp(()->"simple-app");
	
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/simple-app/status/ping"),is("ok"));
		assertThat(stream.limit(1).toList(),equalTo(Arrays.asList("ping : 0")));
		
	}
	
	
	
}
