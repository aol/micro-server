package app.boot.embedded.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.rest.client.nio.NIORestClient;
import com.aol.simple.react.stream.simple.SimpleReact;
import com.google.common.collect.ImmutableList;
//@Component
@TestAppRestResource
@Path("/test-status")
public class TestAppResource {

	private final SimpleReact simpleReact = new SimpleReact();
	private final  NIORestClient  template;
	private final ImmutableList<String> urls = ImmutableList.of("http://localhost:8081/alternative-app/alt-status/ping",
			"http://localhost:8080/test-app/test-status/ping",
			"http://localhost:8082/simple-app/status/ping",
			"http://localhost:8080/test-app/test-status/ping");
	
	@Autowired
	public TestAppResource(NIORestClient template) {
		
		this.template = template;
	}

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
		
		return simpleReact
			.fromStream(urls.stream()
					.map(it ->  template.getForEntity(it,String.class)))
			.then(it -> it.getBody())
			.then(it -> "*"+it)
			.peek(loadedAndModified -> System.out.println(loadedAndModified))
			.block().stream().reduce("", (acc,next) -> acc+"-"+next);
		
	}

}
