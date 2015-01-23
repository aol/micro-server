package embedded.app.com.aol.micro.server;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

@Component
@Path("/alt-status")
public class AltAppResource implements AltAppRestResource {

	@POST
	@Produces("text/plain")
	@Path("/ping")
	public String ping(ImmutableEntity entity) {
		return entity.getList().get(0);
	}

}
