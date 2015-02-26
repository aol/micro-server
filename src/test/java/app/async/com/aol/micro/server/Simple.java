package app.async.com.aol.micro.server;

import com.aol.micro.server.MicroServerStartup;

public class Simple {

	public static void main(String[] args){
		new MicroServerStartup(()->"test-app").run();
	}
}
