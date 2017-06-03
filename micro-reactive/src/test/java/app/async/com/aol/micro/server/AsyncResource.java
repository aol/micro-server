package app.async.com.aol.micro.server;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import cyclops.async.SimpleReact;
import cyclops.stream.FutureStream;
import cyclops.stream.ReactiveSeq;
import org.pcollections.ConsPStack;
import org.pcollections.PStack;
import org.springframework.stereotype.Component;


import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.testing.RestAgent;

@Path("/async")
@Component
public class AsyncResource implements RestResource{

	private final SimpleReact simpleReact =new SimpleReact();
	private final PStack<String> urls = ConsPStack.from(Arrays.asList("http://localhost:8080/async-app/async/ping2",
			"http://localhost:8080/async-app/async/ping",
			"http://localhost:8080/async-app/async/ping",
			"http://localhost:8080/async-app/async/ping"));
    
    	private final RestAgent client = new RestAgent();
    	
        @GET
        @Path("/expensive")
        @Produces("text/plain")
        public void expensive(@Suspended AsyncResponse asyncResponse){
  
        	FutureStream.builder().fromIterable(urls)
					.then(it->client.get(it))
					.onFail(it -> "")
					.peek(it -> 
					System.out.println(it))
					.convertToSimpleReact()
					.allOf(data -> {
						System.out.println(data);
							return asyncResponse.resume(ReactiveSeq.fromIterable(data).join(";")); });
        }
        
        @GET
    	@Produces("text/plain")
    	@Path("/ping")
    	public String ping() {
    		return "test!";
    	}
    	
	
}
