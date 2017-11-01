package app.pure.jdbc.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.spring.datasource.jdbc.SQL;

@Rest
@Path("/persistence")
public class PersistentResource  {

	private final SQL dao;

	@Autowired
	public PersistentResource(SQL dao) {

		this.dao = dao;
	}
	@GET
	@Produces("text/plain")
	@Path("/gen")
	public String gen() {
		dao.getJdbc().execute("create table t_jdbc(id bigint,name varchar(255),value varchar(255),version int);");

		return "ok";
	}
	@GET
	@Produces("text/plain")
	@Path("/create")
	public String createEntity() {
		dao.getJdbc().update("insert into t_jdbc VALUES (1,'hello','world',1)");

		return "ok";
	}

	@GET
	@Produces("application/json")
	@Path("/get")
	public JdbcEntity get() {
		return dao.getJdbc().<JdbcEntity> queryForObject("select * from t_jdbc", new BeanPropertyRowMapper(JdbcEntity.class));
	}

}