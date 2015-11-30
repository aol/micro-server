package app.embedded.com.aol.micro.server;

import java.util.ArrayList;

import javaslang.collection.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

@Component
@Path("/alt-status")
public class AltAppResource implements AltAppRestResource {

	@POST
	@Produces("application/json")
	@Path("/ping")
	public List ping(ImmutableEntity entity) {
		return entity.getList();
	}

}
