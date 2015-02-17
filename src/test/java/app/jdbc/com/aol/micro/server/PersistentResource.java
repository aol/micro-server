package app.jdbc.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.spring.datasource.jdbc.SQL;

@Component
@Path("/persistence")
public class PersistentResource implements RestResource {

	private final SQL dao;

	@Autowired
	public PersistentResource(SQL dao) {

		this.dao = dao;
	}

	@GET
	@Produces("text/plain")
	@Path("/create")
	public String createEntity() {
		dao.update("insert into t_jdbc VALUES (1,'hello','world',1)");
	
		return "ok";
	}

	@GET
	@Produces("application/json")
	@Path("/get")
	public JdbcEntity get() {
		return dao.<JdbcEntity>queryForObject("select * from t_jdbc",new BeanPropertyRowMapper(JdbcEntity.class));
	}

}