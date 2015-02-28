package app.boot.filter.com.aol.micro.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.boot.config.MicrobootApp;
@Configuration
@ComponentScan(basePackages = { "app.filter.com.aol.micro.server" })
public class FilterAppLocalMain {

	

			
			public static void main(String[] args) throws InterruptedException {
				
				new MicrobootApp( FilterAppLocalMain.class, () -> "filter-app")
						.run();
			}

	}