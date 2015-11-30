package app.embedded.com.aol.micro.server;

import javaslang.collection.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.aol.micro.server.testing.RestAgent;
import com.aol.simple.react.stream.simple.SimpleReact;
import com.aol.simple.react.stream.traits.LazyFutureStream;
@Component
@Path("/test-status")
public class TestAppResource implements TestAppRestResource {

	private final SimpleReact simpleReact = new SimpleReact();
	private final  RestAgent template = new RestAgent();
	private final  List<String> urls = List.ofAll("http://localhost:8081/alternative-app/alt-status/ping",
			"http://localhost:8080/test-app/test-status/ping",
			"http://localhost:8082/simple-app/status/ping",
			"http://localhost:8080/test-app/test-status/ping");
	
	

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "test!";
	}
	
	@GET
	@Produces("text/plain")
	@Path("/rest-calls")
	public String restCallResult(){
		
		return LazyFutureStream.lazyFutureStreamFromIterable(urls)
					.map(it ->template.get(it))
					.then(it -> "*"+it)
					.peek(loadedAndModified -> System.out.println(loadedAndModified))
					.block().stream().reduce("", (acc,next) -> acc+"-"+next);
		
	}

}
