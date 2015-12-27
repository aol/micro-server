package com.aol.micro.server.alivehandler;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.auto.discovery.RestResource;

@Rest
@Path("/{name}")
public class AliveHandlerRest implements RestResource {

	private final AliveHandlerControllerRepository repository;

	@Autowired
	public AliveHandlerRest(AliveHandlerControllerRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@GET
	public Response checkAlive(@PathParam("name") String name) {
		Optional<AliveHandlerController> controller = repository.get(name);
		return controller.map(c -> c.process()).orElse(Response.status(404).build());
	}
}
