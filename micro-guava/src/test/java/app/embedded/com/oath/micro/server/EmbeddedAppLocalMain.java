package app.embedded.com.oath.micro.server;

import java.util.Arrays;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.module.EmbeddedModule;

@Microserver(basePackages = {    "app.embedded.com.oath.micro.server" })
public class EmbeddedAppLocalMain {

	
	public static void main(String[] args) throws InterruptedException {
		
		new MicroserverApp(
				EmbeddedModule.tagInterfaceModule(Arrays.asList(TestAppRestResource.class),"test-app"),
				EmbeddedModule.tagInterfaceModule(Arrays.asList(AltAppRestResource.class),"alternative-app")).start();

		

	}

	
}
