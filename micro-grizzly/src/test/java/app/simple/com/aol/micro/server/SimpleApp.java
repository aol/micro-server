package app.simple.com.aol.micro.server;

import java.util.Arrays;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.module.ConfigurableModule;

public class SimpleApp {

	public static void main(String[] args){
		
		new MicroserverApp(()-> "simple-app").run();
	}
	
	
}
