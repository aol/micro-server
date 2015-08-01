package app.swagger.com.aol.micro.server;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.RestResource;
import com.google.common.collect.ImmutableList;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


@Path("/stats")
@Component
@Api(value = "/stats", description = "Resource to show stats for a box using sigar")
public class StatsResource implements RestResource {

	

	@GET
	@Path("/ping")
	@Produces("application/json")
	@ApiOperation(value = "Make a ping call", response = List.class)
	public List<Integer> getMachineStats() {
		return  ImmutableList.of(1);
	}
}
