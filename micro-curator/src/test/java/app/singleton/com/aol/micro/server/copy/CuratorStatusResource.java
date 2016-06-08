package app.singleton.com.aol.micro.server.copy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.curator.lock.CuratorDistributedLockServiceProvider;
import com.aol.micro.server.dist.lock.DistributedLockService;

@Rest(isSingleton=true)
@Path("/status")
public class CuratorStatusResource {

	private final DistributedLockService service;
	private final DistributedLockService service2;
	
	@Autowired
	public CuratorStatusResource(CuratorDistributedLockServiceProvider provider) {
		this.service = provider.getDistributedLock(1_000);
		this.service2 = provider.getDistributedLock(1_000);
	}
	@GET
	@Path("/lock")
	public String lock() {
		if(service.tryLock("hello2"))
			return "got";
		return "not";
		
	}

	@GET
	@Path("/lock2")
	public String lock2() {
		if(service2.tryLock("hello2"))
			return "got";
		return "not";
		
	}
	
}