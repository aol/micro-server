package app.validation.com.aol.micro.server;

import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import app.javaslang.com.aol.micro.server.ImmutableJavaslangEntity;

import com.aol.micro.server.auto.discovery.RestResource;
@Component
@Path("/status")
public class ValidationAppResource implements RestResource {

	@POST
	@Produces("application/json")
	@Path("/ping")
	public ImmutableJavaslangEntity ping( @NotNull ImmutableJavaslangEntity entity) {
		return entity;
	}
	

}
