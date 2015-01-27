package embedded.app.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;
@Component
@Path("/test-status")
public class TestAppResource implements TestAppRestResource {

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		return "test!";
	}

}
