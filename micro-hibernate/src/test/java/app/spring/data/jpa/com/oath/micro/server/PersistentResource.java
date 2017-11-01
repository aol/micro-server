package app.spring.data.jpa.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.oath.micro.server.auto.discovery.Rest;

@Rest
@Path("/persistence")
public class PersistentResource  {

	
	private final SpringDataRepository dao;
	
	@Autowired
	public PersistentResource(SpringDataRepository dao) {
	
		this.dao = dao;
	}
	
	@GET
	@Produces("text/plain")
	@Path("/create")
	public String createEntity() {
		
		SpringDataEntity saved = dao.save(SpringDataEntity.builder()
								.name("test")
								.value("value").build());
		
		return "ok";
	}
	@GET
	@Produces("application/json")
	@Path("/get")
	public Iterable<SpringDataEntity> get(){
		
		return dao.findAll();
		
		
	}

	
}