package app.s3.manifest.comparator.com.aol.micro.server.second;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.manifest.ManifestComparator;

@Path("/comparator2")
@Rest
public class SecondComparatorResource {

	private final ManifestComparator comparator;

	@Autowired
	public SecondComparatorResource(ManifestComparator comparator) {
		this.comparator = comparator.withKey("test-key5");
	}

	@GET
	@Path("/increment")
	public String bucket() {
		comparator.saveAndIncrement("hellob");
		return "increment";
	}

	@GET
	@Path("/get")
	public String get() {
		comparator.load();
		return comparator	.getData()
							.toString();

	}

	@GET
	@Path("/check")
	public String check() {
		return "" + !comparator.isOutOfDate();

	}
}