package app.boot.com.aol.micro.server;

import com.aol.micro.server.boot.config.MicrobootApp;


public class SimpleApp {

	public static void main(String[] args){
		new MicrobootApp(()->"test-app").run();
	}
	
}
