package app.com.aol.micro.server;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.MicroServerStartup;

@Configuration
@ComponentScan(basePackages = { "app.com.aol.micro.server" })
public class AppRunnerTest {

		
		public static void main(String[] args) throws InterruptedException {
			new MicroServerStartup( AppRunnerTest.class, () -> "test-app")
					.start();
		}

}
