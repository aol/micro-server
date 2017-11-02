package app.boot.embedded.com.oath.micro.server;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@AltAppRestResource
@Path("/alt-status")
public class AltAppResource {

	@POST
	@Produces("application/json")
	@Path("/ping")
	public List ping(ImmutableEntity entity) {
		return entity.getList();
	}

}
