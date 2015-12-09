package app.couchbase.manifest.comparator.com.aol.micro.server.copy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.couchbase.DistributedMapClient;
import com.aol.micro.server.couchbase.base.ManifestComparator;
import com.aol.micro.server.couchbase.base.VersionedKey;
import com.aol.micro.server.rest.jackson.JacksonUtil;

@Path("/comparator")
@Rest
public class ManifestComparatorResource {
	

	private volatile int count = 1;
	private final ManifestComparator comparator;
	@Autowired
	public  ManifestComparatorResource(ManifestComparator comparator) {
		this.comparator = comparator.withKey("test-key");
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
