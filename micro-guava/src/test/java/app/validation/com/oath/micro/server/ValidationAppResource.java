package app.validation.com.oath.micro.server;

import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import app.guava.com.oath.micro.server.ImmutableGuavaEntity;

import com.oath.micro.server.auto.discovery.RestResource;
@Component
@Path("/status")
public class ValidationAppResource implements RestResource {

	@POST
	@Produces("application/json")
	@Path("/ping")
	public ImmutableGuavaEntity ping( @NotNull ImmutableGuavaEntity entity) {
		return entity;
	}
	

}
