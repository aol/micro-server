package app.async.com.aol.micro.server;

import com.aol.micro.server.MicroserverApp;

public class Simple {

	public static void main(String[] args){
		new MicroserverApp(()->"test-app").run();
	}
}
