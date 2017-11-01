package app.single.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.mysql.distlock.DistributedLockServiceMySqlImpl;

@Rest
@Path("/status")
public class SimpleStatusResource {
	@Autowired
	DistributedLockServiceMySqlImpl lock;
	
	

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {

		return ""+ (lock!=null);
	}

	
}