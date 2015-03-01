package app.servlet.com.aol.micro.server;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.boot.config.MicrobootApp;

@Configuration
@ComponentScan(basePackages = { "app.servlet.com.aol.micro.server" })
public class AppRunnerLocalMain {

		
		public static void main(String[] args) throws InterruptedException {
			
			new MicrobootApp( AppRunnerLocalMain.class, () -> "test-app")
					.run();
		}

}
