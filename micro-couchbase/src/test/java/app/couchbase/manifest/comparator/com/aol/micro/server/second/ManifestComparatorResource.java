package app.couchbase.manifest.comparator.com.aol.micro.server.second;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.manifest.ManifestComparator;

@Path("/comparator")
@Rest
public class ManifestComparatorResource {
	

	private volatile int count = 1;
	private final ManifestComparator<String> comparator;
	@Autowired
	public  ManifestComparatorResource(ManifestComparator comparator) {
		this.comparator = comparator.<String>withKey("test-key");
	}
	@GET
	@Path("/increment")
	public String bucket(){
		comparator.saveAndIncrement("hello"+(count++));
		return "increment";
	}
	@GET
	@Path("/get")
	public String get(){
		comparator.load();
		return comparator.getData().toString();
		
	}
	@GET
	@Path("/check")
	public String check(){
		return ""+!comparator.isOutOfDate();
		
	}
}
