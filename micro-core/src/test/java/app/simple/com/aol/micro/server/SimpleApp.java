package app.simple.com.aol.micro.server;

import com.aol.micro.server.MicroserverApp;

public class SimpleApp {

	public static void main(String[] args){
		new MicroserverApp(()-> "simple-app").run();
	}
	
	
}
