package app.listeners.com.oath.micro.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import lombok.Getter;

import org.springframework.stereotype.Component;

@Component
public class AutodiscoveredListener  implements ServletContextListener {

	@Getter
	private static volatile int called= 0;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		called ++;
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		
	}
	
	

	


	

}