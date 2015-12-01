package app.boot.embedded.com.aol.micro.server;

import java.util.Arrays;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.boot.config.Microboot;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.EmbeddedModule;

@Microserver @Microboot//(basePackages = {    "app.boot.embedded.com.aol.micro.server" })
public class EmbeddedAppLocalMain {

	
	public static void main(String[] args) throws InterruptedException {
		
		new MicroserverApp(
				new EmbeddedModule(Arrays.asList(TestAppRestResource.class),"test-app"),
				new EmbeddedModule(Arrays.asList(AltAppRestResource.class),"alternative-app")).start();

		

	}

	
}
