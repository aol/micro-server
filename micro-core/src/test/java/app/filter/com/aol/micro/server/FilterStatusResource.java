package app.filter.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.aol.micro.server.auto.discovery.RestResource;

@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Path("/status")
public class FilterStatusResource implements RestResource {

	@Autowired
	private RequestScopeUserInfo info;
	
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		info.print();
		return "ok";
	}
	@GET
	@Produces("text/plain")
	@Path("/ping2")
	public String ping2() {
		return "ok";
	}

}