package app.simple.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.rest.client.com.aol.micro.server.MyEntity;

import com.aol.micro.server.auto.discovery.RestResource;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Component
@Path("/status")
public class SimpleStatusResource implements RestResource {

	
	
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		
		return "ok";
	}

	
}