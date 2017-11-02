package app.async.com.oath.micro.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.auto.discovery.Rest;



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
