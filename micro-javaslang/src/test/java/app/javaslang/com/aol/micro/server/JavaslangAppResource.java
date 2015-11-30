package app.javaslang.com.aol.micro.server;

import javaslang.collection.List;
import javaslang.control.Option;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.aol.micro.server.auto.discovery.Rest;
@Rest
@Path("/status")
public class JavaslangAppResource {

	@POST
	@Produces("application/json")
	@Path("/ping")
	
	public List<String> ping( ImmutableJavaslangEntity entity) {
		return entity.getList();
	}
	@POST
	@Produces("application/json")
	@Path("/optional")
	public Option<String> optional(JavaslangEntity entity) {
		return entity.getName();
	}

}
