package app.rest.client.com.aol.micro.server;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

@Component
@Path("/generics")
public class RestClientResource implements RestResource {

	private final ImmutableList<MyEntity> result = ImmutableList.of(new MyEntity());
	@GET
	@Produces("application/json")
	@Path("/get")
	public ImmutableList<MyEntity> get() {
		
		return result;
	}
	
	@POST
	@Produces("application/json")
	@Path("/post")
	public ImmutableList<MyEntity> post(ImmutableMap<Integer,String> map) {
		
		return result;
	}
	
	@PUT
	@Produces("application/json")
	@Path("/put")
	public ImmutableList<MyEntity> put(ImmutableMap<Integer,String> map) {
		
		return result;
	}
	@DELETE
	@Produces("application/json")
	@Path("/delete")
	public ImmutableList<MyEntity> delete(ImmutableMap<Integer,String> map) {
		
		return result;
	}
	

}