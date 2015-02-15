package app.spring.data.jpa.com.aol.micro.server;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.spring.datasource.hibernate.DAOProvider;
import com.aol.micro.server.spring.datasource.hibernate.GenericHibernateService;
import com.googlecode.genericdao.search.Search;

@Component
@Path("/persistence")
public class PersistentResource implements RestResource {

	
	private final HibernateEntityRepository dao;
	
	@Autowired
	public PersistentResource(HibernateEntityRepository dao) {
	
		this.dao = dao;
	}
	
	@GET
	@Produces("text/plain")
	@Path("/create")
	public String createEntity() {
		
		dao.save(HibernateEntity.builder()
								.name("test")
								.value("value").build());
		return "ok";
	}
	@GET
	@Produces("application/json")
	@Path("/get")
	public Iterable<HibernateEntity> get(){
		
		return dao.findAll();
		
		
	}

	
}