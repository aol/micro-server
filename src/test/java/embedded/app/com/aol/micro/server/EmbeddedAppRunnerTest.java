package embedded.app.com.aol.micro.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import app.com.aol.micro.server.AppRunnerTest;

import com.aol.micro.server.EmbeddedModule;
import com.aol.micro.server.MicroServerStartup;

@Configuration
@ComponentScan(basePackages = { "embedded.app.com.aol.micro.server" })
public class EmbeddedAppRunnerTest {

	public static void main(String[] args) throws InterruptedException {
		new MicroServerStartup(EmbeddedAppRunnerTest.class, 
				new EmbeddedModule(TestAppRestResource.class,"test-app"),
				new EmbeddedModule(AltAppRestResource.class,"alternative-app")).start();

		

	}

	
}
