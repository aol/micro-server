package app.async.com.aol.micro.server;

import java.io.IOException;
import java.util.Properties;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Config;
import com.aol.micro.server.spring.properties.PropertyFileConfig;

public class Simple {

	public static void main(String[] args) throws IOException{
		
		new MicroserverApp(()->"test-app").run();
	}
}
