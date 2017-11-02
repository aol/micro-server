package app.simple.com.oath.micro.server;

import com.oath.micro.server.MicroserverApp;

public class SimpleApp {

	public static void main(String[] args){
		
		new MicroserverApp(()-> "simple-app").run();
	}
	
	
}
