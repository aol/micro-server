package app.embedded.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.rest.client.nio.SpringRestTemplate;
import com.aol.simple.react.SimpleReact;
import com.google.common.collect.ImmutableList;
@Component
@Path("/test-status")
public class TestAppResource implements TestAppRestResource {

	
	private final  SpringRestTemplate  template;
	private final ImmutableList<String> urls = ImmutableList.of("http://localhost:8081/alternative-app/alt-status/ping",
			"http://localhost:8080/test-app/test-status/ping",
			"http://localhost:8082/simple-app/status/ping",
			"http://localhost:8080/test-app/test-status/ping");
	
	@Autowired
	public TestAppResource(SpringRestTemplate template) {
		
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
		
		return new SimpleReact()
			.fromStream(urls.stream()
					.map(it ->  template.getForEntity(it,String.class)))
			.then(it -> it.getBody())
			.then(it -> "*"+it)
			.peek(loadedAndModified -> System.out.println(loadedAndModified))
			.block().stream().reduce("", (acc,next) -> acc+"-"+next);
		
	}

}
