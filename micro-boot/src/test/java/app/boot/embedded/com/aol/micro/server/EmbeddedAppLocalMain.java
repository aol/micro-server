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
				EmbeddedModule.tagInterfaceModule(Arrays.asList(TestAppRestResource.class),"test-app"),
				EmbeddedModule.tagInterfaceModule(Arrays.asList(AltAppRestResource.class),"alternative-app")).start();

		

	}

	
}
