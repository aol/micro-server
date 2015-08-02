package app.reactive.pipes.com.aol.micro.server;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.reactive.Pipes;
import com.aol.micro.server.testing.RestAgent;
import com.aol.simple.react.async.Queue;
import com.aol.simple.react.stream.traits.LazyFutureStream;

@Microserver
public class PipesRunnerTest {

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	LazyFutureStream<String> stream;
	@Before
	public void startServer(){
		stream = Pipes.register("test", new Queue<String>());
		
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
