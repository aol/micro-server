package app.listeners.com.aol.micro.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import lombok.Getter;

public class ConfiguredListener implements ServletContextListener {

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