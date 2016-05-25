package app.embedded.com.aol.micro.server;

import com.aol.cyclops.data.collections.extensions.persistent.PStackX;
import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.module.EmbeddedModule;

@Microserver(basePackages = {    "app.embedded.com.aol.micro.server" })
public class EmbeddedAppLocalMain {

	
	public static void main(String[] args) throws InterruptedException {
		
		new MicroserverApp(
				EmbeddedModule.tagInterfaceModule(PStackX.of(TestAppRestResource.class),"test-app"),
				EmbeddedModule.tagInterfaceModule(PStackX.of(AltAppRestResource.class),"alternative-app")).start();

		

	}

	
}
