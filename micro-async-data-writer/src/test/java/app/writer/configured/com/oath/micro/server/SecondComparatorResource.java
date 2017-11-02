package app.writer.configured.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.manifest.ManifestComparator;

@Path("/comparator2")
@Rest
public class SecondComparatorResource {

    private final ManifestComparator comparator;

    @Autowired
    public SecondComparatorResource(ManifestComparator comparator) {
        this.comparator = comparator.withKey("test-key");
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
        return comparator.getData()
                         .toString();

    }

    @GET
    @Path("/check")
    public String check() {
        return "" + !comparator.isOutOfDate();

    }
}