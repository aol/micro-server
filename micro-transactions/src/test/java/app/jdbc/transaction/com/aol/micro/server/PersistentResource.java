package app.jdbc.transaction.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.spring.datasource.jdbc.SQL;
import com.oath.micro.server.transactions.TransactionFlow;

@Rest
@Path("/persistence")
public class PersistentResource  {

	private final SQL dao;
	private final TransactionFlow<String, String> flow;

	@Autowired
	public PersistentResource(TransactionFlow flow,SQL dao) {

		this.dao = dao;
		this.flow=flow;
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
		
		flow.map(name->dao.getJdbc().update("insert into t_jdbc VALUES (1,'"+name+"+','world',1)"))
			.execute("hello");
		

		return "ok";
	}

	@GET
	@Produces("application/json")
	@Path("/get")
	public JdbcEntity get() {
		return dao.getJdbc().<JdbcEntity> queryForObject("select * from t_jdbc", new BeanPropertyRowMapper(JdbcEntity.class));
	}

}