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
	
	public void test2(){
		
		
		String logFile = readNextLog();
		ImpressionData impressionData = parse(logFile);
		UserData userData = extractUsers(impressionData);
		save(userData);
						
		
		
	}
	
	
	public void simpleReact(){
		
		
		new SimpleReact().react( ()->1, ()->2, ()->3);
		
		
		new SimpleReact(new ForkJoinPool(4)).react(()->1, ()->2, ()->3);
		
		
		
		
		
		
		
	}
	
	public void test3(){
		
		
		
		
		
		Arrays.<Supplier<String>>asList(()->readNextLog(),
                ()->readNextLog())
                .stream()
                .parallel()
				.map(supplier -> supplier.get())
				.map(logFile ->  parse(logFile))
				.map(impressionData -> extractUsers(impressionData))
				.forEach(userData -> save(userData));
		
						
		
		
	}
	
	
	public void test4(){
		
	
		
		
		new SimpleReact().react(()->readNextLog(),
                					()->readNextLog())
                		.then(logFile ->  parse(logFile))
                		.then(impressionData -> extractUsers(impressionData))
                		.peek(userData -> save(userData));
		
						
	
		
		
		
	}
	
	
	public void brokenOut(){
		
		
		CompletableFuture<String> logFileFuture = CompletableFuture.supplyAsync(() -> readNextLog());
		CompletableFuture<ImpressionData> impressionDataFuture  = logFileFuture.thenApplyAsync(logFile -> parse(logFile));
		CompletableFuture<UserData>  userDataFuture = impressionDataFuture.thenApplyAsync(impressionData -> extractUsers(impressionData));
		userDataFuture.thenAcceptAsync(userData -> save(userData));
		
		
		
		
	}
	
	public void test(){
		
		
		CompletableFuture.supplyAsync(() -> readNextLog())
						.thenApplyAsync(logFile -> parse(logFile))
						.thenApplyAsync(impressionData -> extractUsers(impressionData))
						.thenAcceptAsync(userData -> save(userData));
		
		//continue processing while read / parse / extract /save happens asynchronously
		
		
	}
	
	static class ImpressionData {}
	static class UserData {}
	
	
	private Object save(Object userData) {
		// TODO Auto-generated method stub
		return null;
	}
	private UserData extractUsers(Object impressionData) {
		// TODO Auto-generated method stub
		return null;
	}
	private ImpressionData parse(String logFile) {
		// TODO Auto-generated method stub
		return null;
	}
	private String readNextLog() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
