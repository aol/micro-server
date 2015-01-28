package embedded.app.com.aol.micro.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import app.com.aol.micro.server.AppRunnerLocalMain;

import com.aol.micro.server.MicroServerStartup;
import com.aol.micro.server.module.EmbeddedModule;

@Configuration
@ComponentScan(basePackages = { "embedded.app.com.aol.micro.server" })
public class EmbeddedAppRunnerTest {

	public static void main(String[] args) throws InterruptedException {
		new MicroServerStartup(EmbeddedAppRunnerTest.class, 
				new EmbeddedModule(TestAppRestResource.class,"test-app"),
				new EmbeddedModule(AltAppRestResource.class,"alternative-app")).start();

		

	}

	
}
