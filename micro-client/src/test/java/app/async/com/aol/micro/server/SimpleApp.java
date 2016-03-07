package app.async.com.aol.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.Rest;



@Rest
@Path("/test")
public class SimpleApp {

	public static void main(String[] args){
		new MicroserverApp(()->"test-app").run();
	}
	@GET
	public String myEndPoint(){
		return "hello world!";
	}

	
}
