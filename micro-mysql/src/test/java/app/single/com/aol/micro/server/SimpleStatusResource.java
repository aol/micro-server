package app.single.com.aol.micro.server;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.mysql.distlock.DistributedLockServiceMySqlImpl;

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