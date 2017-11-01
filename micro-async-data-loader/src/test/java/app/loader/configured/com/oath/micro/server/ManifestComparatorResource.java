package app.loader.configured.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.manifest.ManifestComparator;

@Path("/comparator")
@Rest
public class ManifestComparatorResource {

    private volatile int count = 1;
    private final ManifestComparator<String> comparator;

    @Autowired
    public ManifestComparatorResource(ManifestComparator comparator) {
        this.comparator = comparator;
    }

    @GET
    @Path("/increment")
    public String bucket() {
        comparator.saveAndIncrement("hello" + (count++));
        return "incremented";
    }

    @GET
    @Path("/get")
    public String get() {
        return comparator.getData();
    }

    @GET
    @Path("/check")
    public String check() {
        return "" + !comparator.isOutOfDate();

    }
}
