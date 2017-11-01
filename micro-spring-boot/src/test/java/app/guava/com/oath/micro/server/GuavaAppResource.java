package app.guava.com.oath.micro.server;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.RestResource;
@Component
@Path("/status")
public class GuavaAppResource implements RestResource {

	@POST
	@Produces("application/json")
	@Path("/ping")
	public List ping(ImmutableGuavaEntity entity) {
		return entity.getList();
	}
	@POST
	@Produces("application/json")
	@Path("/optional")
	public Optional<String> optional(Jdk8Entity entity) {
		return entity.getName();
	}

}
