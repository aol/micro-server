package app.embedded.com.aol.micro.server;


import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.EmbeddedModule;
import cyclops.collections.immutable.LinkedListX;

@Microserver(basePackages = {    "app.embedded.com.aol.micro.server" })
public class EmbeddedAppLocalMain {

	
	public static void main(String[] args) throws InterruptedException {
		
		new MicroserverApp(
				EmbeddedModule.tagInterfaceModule(LinkedListX.of(TestAppRestResource.class),"test-app"),
				EmbeddedModule.tagInterfaceModule(LinkedListX.of(AltAppRestResource.class),"alternative-app")).start();

		

	}

	
}
