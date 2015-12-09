package app.couchbase.manifest.comparator.com.aol.micro.server.copy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.couchbase.base.ManifestComparator;
import com.aol.micro.server.couchbase.base.VersionedKey;
import com.aol.micro.server.rest.jackson.JacksonUtil;


@Path("/comparator2")
@Rest
public class SecondComparatorResource {
	
	
	private final ManifestComparator comparator;
	@Autowired
	public  SecondComparatorResource(ManifestComparator comparator) {
		this.comparator = comparator.withKey("test-key");
	}
	@GET
	@Path("/increment")
	public String bucket(){
		comparator.saveAndIncrement("hellob");
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