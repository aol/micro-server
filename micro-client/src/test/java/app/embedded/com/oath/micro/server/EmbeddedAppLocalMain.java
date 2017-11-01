package app.embedded.com.oath.micro.server;


import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.module.EmbeddedModule;
import cyclops.collections.immutable.LinkedListX;

@Microserver(basePackages = {    "app.embedded.com.aol.micro.server" })
public class EmbeddedAppLocalMain {

	
	public static void main(String[] args) throws InterruptedException {
		
		new MicroserverApp(
				EmbeddedModule.tagInterfaceModule(LinkedListX.of(TestAppRestResource.class),"test-app"),
				EmbeddedModule.tagInterfaceModule(LinkedListX.of(AltAppRestResource.class),"alternative-app")).start();

		

	}

	
}
