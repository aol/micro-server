package app.async.com.oath.micro.server;

import java.io.IOException;

import com.oath.micro.server.MicroserverApp;

public class Simple {

	public static void main(String[] args) throws IOException{
		
		new MicroserverApp(()->"test-app").run();
	}
}
