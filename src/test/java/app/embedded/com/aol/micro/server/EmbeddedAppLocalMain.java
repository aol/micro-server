package app.embedded.com.aol.micro.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import app.servlet.com.aol.micro.server.AppRunnerLocalMain;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.module.EmbeddedModule;

@Configuration
@ComponentScan(basePackages = {    "app.embedded.com.aol.micro.server" })
public class EmbeddedAppLocalMain {

	
	public static void main(String[] args) throws InterruptedException {
		
		new MicroServerStartup(EmbeddedAppLocalMain.class, 
				new EmbeddedModule(TestAppRestResource.class,"test-app"),
				new EmbeddedModule(AltAppRestResource.class,"alternative-app")).start();

		

	}

	
}
