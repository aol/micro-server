package app.servlet.com.aol.micro.server;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.oath.micro.server.MicroserverApp;

@Configuration
@ComponentScan(basePackages = { "app.servlet.com.aol.micro.server" })
public class AppRunnerLocalMain {

		
		public static void main(String[] args) throws InterruptedException {
			
			new MicroserverApp( AppRunnerLocalMain.class, () -> "test-app")
					.run();
		}

}
