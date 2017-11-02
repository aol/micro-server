package app.blacklisted.com.oath.micro.server.copy;

import com.oath.micro.server.MicroserverApp;

public class SimpleApp {

	public static void main(String[] args){
		
		new MicroserverApp(()-> "simple-app").run();
	}
	
	
}
