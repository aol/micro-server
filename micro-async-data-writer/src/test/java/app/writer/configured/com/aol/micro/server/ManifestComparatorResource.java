package app.writer.configured.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.async.data.writer.AsyncDataWriter;
import com.aol.micro.server.auto.discovery.Rest;

@Path("/comparator")
@Rest
public class ManifestComparatorResource {
	

	private volatile int count = 1;
	private final AsyncDataWriter<String> comparator;
	@Autowired
	public  ManifestComparatorResource(AsyncDataWriter comparator) {
		this.comparator = comparator;
	}
	@GET
	@Path("/increment")
	public String bucket(){
		comparator.saveAndIncrement("hello"+(count++)).get();
		return "incremented";
	}
	@GET
	@Path("/get")
	public String get(){
		
		return comparator.loadAndGet().get();
		
	}
	@GET
	@Path("/check")
	public String check(){
		return ""+!comparator.isOutOfDate().get();
		
	}
}
