package app.singleton.com.aol.micro.server.copy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.curator.lock.CuratorDistributedLockServiceProvider;
import com.aol.micro.server.utility.DistributedLockService;

@Rest(isSingleton=true)
@Path("/status")
public class CuratorStatusResource {

	private final DistributedLockService service;
	@Autowired
	public CuratorStatusResource(CuratorDistributedLockServiceProvider provider) {
		this.service = provider.getDistributedLock(1_000);
	}
	@GET
	@Path("/ping")
	public String ping() {
		if(service.tryLock("hello2"))
			return "got";
		return "not";
		
	}

	
}