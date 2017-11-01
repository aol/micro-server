package app.embedded.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import cyclops.async.SimpleReact;
import cyclops.reactive.FutureStream;
import org.springframework.stereotype.Component;


import com.oath.micro.server.testing.RestAgent;
import com.google.common.collect.ImmutableList;
@Component
@Path("/test-status")
public class TestAppResource implements TestAppRestResource {

	private final SimpleReact simpleReact = new SimpleReact();
	private final  RestAgent template = new RestAgent();
	private final ImmutableList<String> urls = ImmutableList.of("http://localhost:8081/alternative-app/alt-status/ping",
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
		
		return FutureStream.builder().fromIterable(urls)
					.map(it ->template.get(it))
					.then(it -> "*"+it)
					.peek(loadedAndModified -> System.out.println(loadedAndModified))
					.block().stream().reduce("", (acc,next) -> acc+"-"+next);
		
	}

}
