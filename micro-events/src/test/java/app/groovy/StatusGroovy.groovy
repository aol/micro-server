package app.groovy

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

import com.oath.micro.server.MicroserverApp
import com.oath.micro.server.auto.discovery.Rest
import com.oath.micro.server.config.Microserver
import com.oath.micro.server.module.Module

@Rest
@Path("/status")
@Microserver
class StatusGroovy {

	static void main(String[] args){
		
		def app = new MicroserverApp(StatusGroovy,{ -> "status-app"} as Module).start()
		
	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String respondToPing() {
		return "pong!"
	}
}
