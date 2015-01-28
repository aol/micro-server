package app.com.aol.micro.server;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.MicroServerStartup;
import com.aol.simple.react.SimpleReact;

@Configuration
@ComponentScan(basePackages = { "app.com.aol.micro.server" })
public class AppRunnerLocalMain {

		
		public static void main(String[] args) throws InterruptedException {
			
			new MicroServerStartup( AppRunnerLocalMain.class, () -> "test-app")
					.start();
		}

}
