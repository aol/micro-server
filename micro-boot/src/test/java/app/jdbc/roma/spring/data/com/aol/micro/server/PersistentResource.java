package app.jdbc.roma.spring.data.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.spring.datasource.jdbc.SQL;

@Component
@Path("/persistence")
public class PersistentResource implements RestResource {

	private final JdbcEntityRepository dao;
	private final SQL sql;
	@Autowired
	public PersistentResource(JdbcEntityRepository dao, SQL sql) {

		this.dao = dao;
		this.sql = sql;
	}

	@GET
	@Produces("text/plain")
	@Path("/create")
	public String createEntity() {
		
	
		dao.save(JdbcEntity.builder()
				.name("test")
				.value("value").id(1l).version(2).build());

	
		return "ok";
	}

	@GET
	@Produces("application/json")
	@Path("/get")
	public JdbcEntity get() {
		return dao.findAll().get(0);	
	}

}