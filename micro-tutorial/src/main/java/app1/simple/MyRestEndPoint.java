package app1.simple;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import cyclops.async.LazyReact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;


import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.events.RequestEvents;
import com.aol.micro.server.ip.tracker.QueryIPRetriever;
import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.aol.micro.server.spring.datasource.jdbc.SQL;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Rest
@Path("/mypath")
@Api(value = "/mypath", description = "Resource to show stats for a box using sigar")
public class MyRestEndPoint {
	
	private final EventBus bus;
	private final AtomicLong correlationProvider = new AtomicLong(0);
	private final SQL dao;
	private final DataService dataService;
	
	@Autowired
	public MyRestEndPoint(@Qualifier("microserverEventBus")final EventBus bus,final SQL dao, final DataService dataService) {
		this.bus = bus;
		this.dao = dao;
		this.dataService = dataService;
	}

	@GET
	@Produces("text/plain")
	@Path("/hello")
	@ApiOperation(value = "Hello world", response = String.class)
	public String hello(){
		long correlationId = correlationProvider.incrementAndGet();
		bus.post(RequestEvents.start(QueryIPRetriever.getIpAddress(),correlationId));
		try{
			return "world";
		}finally{
			bus.post(RequestEvents.finish("success",correlationId));

		}
	}
	
	
	@GET
	@Produces("text/plain")
	@Path("/create")
	@ApiOperation(value = "Create db entity", response = String.class)
	public String createEntity() {
		dao.getJdbc().update("insert into t_jdbc VALUES (1,'hello','world',1)");
	
		return "ok";
	}

	@GET
	@Produces("application/json")
	@Path("/get")
	@ApiOperation(value = "Query for single entity", response = Entity.class)
	public Entity get() {
		return dao.getJdbc().<Entity>queryForObject("select * from t_jdbc",new BeanPropertyRowMapper(Entity.class));
	}
	
	@GET
	@Produces("text/plain")
	@Path("/create-entity")
	@ApiOperation(value = "Create a hibernate entity", response = String.class)
	public String createEntityHibernate(@QueryParam("name") String name,@QueryParam("value") String value) {
		this.dataService.createEntity(name, value);
	
		return "ok";
	}

	@GET
	@Produces("application/json")
	@Path("/findAll")
	@ApiOperation(value = "Find by name", response = Entity.class)
	public ImmutableList<Entity> findByName(@QueryParam("name")String name) {
		return this.dataService.findAll(name);
	}
	
	 @GET
     @Path("/expensive")
     @Produces("application/json")
	 @ApiOperation(value = "Do Expensive operation", response = List.class)
     public void expensiveDb(@Suspended AsyncResponse asyncResponse){
     		new LazyReact(1,10).ofAsync(()-> dataService.findAll("time"))
     						.map(list -> JacksonUtil.serializeToJson(list))
     						.peek(asyncResponse::resume);
     		
     					
	
     	     	
     }

	

	
}
