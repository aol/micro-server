package app.blacklisted.com.aol.micro.server.copy;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nonautoscan.com.aol.micro.server.ScheduleAndAsyncConfig;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.junit.Assert;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.aol.micro.server.auto.discovery.Rest;

@Rest
@Path("/status")
public class SimpleStatusResource {

	@Autowired
	ApplicationContext context;
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {

		try{
			context.getBean(ScheduleAndAsyncConfig.class);
			Assert.fail("failed to remove ScheduleAndAsyncConfig bean!");
		}catch(NoSuchBeanDefinitionException e){
			
		}
		return "ok";
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/file")
	public String create(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		return "done";
	}
}