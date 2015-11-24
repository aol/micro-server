package app.blacklisted.com.aol.micro.server.copy;

import java.util.Arrays;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.module.ConfigurableModule;

public class SimpleApp {

	public static void main(String[] args){
		
		new MicroserverApp(()-> "simple-app").run();
	}
	
	
}
