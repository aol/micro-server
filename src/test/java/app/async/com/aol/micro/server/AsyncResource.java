package app.async.com.aol.micro.server;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.rest.client.JaxRsNIOClient;
import com.aol.simple.react.SimpleReact;
import com.google.common.collect.ImmutableList;

@Path("/async")
@Component
public class AsyncResource implements RestResource{

	private final ImmutableList<String> urls = ImmutableList.of("http://localhost:8080/async-app/async/ping",
			"http://localhost:8080/async-app/async/ping",
			"http://localhost:8080/async-app/async/ping",
			"http://localhost:8080/async-app/async/ping");
    
    	private final JaxRsNIOClient client = new JaxRsNIOClient(100,100).withAccept("text/plain");
    	
        @GET
        @Path("/expensive")
        @Produces("text/plain")
        public void expensive(@Suspended AsyncResponse asyncResponse){
        	
        	new SimpleReact().fromStream(urls.stream()
					.<CompletableFuture<String>>map(it ->  client.<String>get(it)))
					.onFail(it -> "")
					.peek(it -> 
					System.out.println(it))
					.allOf((List<String> data) -> {
						System.out.println(data);
							return asyncResponse.resume(String.join(";", data)); });
        	
        }
        
        @GET
    	@Produces("text/plain")
    	@Path("/ping")
    	public String ping() {
    		return "test!";
    	}
    	
	
}
