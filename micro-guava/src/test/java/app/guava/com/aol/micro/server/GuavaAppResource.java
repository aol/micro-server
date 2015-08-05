package app.guava.com.aol.micro.server;

import java.util.Optional;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.aol.micro.server.auto.discovery.Rest;
import com.google.common.collect.ImmutableList;
@Rest
@Path("/status")
public class GuavaAppResource {

	@POST
	@Produces("application/json")
	@Path("/ping")
	
	public ImmutableList<String> ping( ImmutableGuavaEntity entity) {
		return entity.getList();
	}
	@POST
	@Produces("application/json")
	@Path("/optional")
	public Optional<String> optional(Jdk8Entity entity) {
		return entity.getName();
	}

}
