package app.async.com.aol.micro.server;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.simple.react.stream.simple.SimpleReact;



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
