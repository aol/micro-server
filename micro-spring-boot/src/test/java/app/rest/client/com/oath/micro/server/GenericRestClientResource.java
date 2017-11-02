package app.rest.client.com.oath.micro.server;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.RestResource;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

@Component
@Path("/rest")
public class GenericRestClientResource implements RestResource {

	
	@GET
	@Produces("application/json")
	@Path("/get")
	public List<String> get() {
		
		return ImmutableList.of("ok");
	}
	
	@POST
	@Produces("application/json")
	@Path("/post")
	public ImmutableSet<String> post(ImmutableMap<Integer,String> map) {
		
		return ImmutableSet.copyOf(map.values());
	}
	
	@PUT
	@Produces("application/json")
	@Path("/put")
	public ImmutableSet<String> put(ImmutableMap<Integer,String> map) {
		
		return ImmutableSet.copyOf(map.values());
	}
	@DELETE
	@Produces("application/json")
	@Path("/delete")
	public List<String> delete(ImmutableMap<Integer,String> map) {
		
		return ImmutableList.of("ok");
	}
	

}