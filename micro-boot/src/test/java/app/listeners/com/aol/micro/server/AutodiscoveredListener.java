package app.listeners.com.aol.micro.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.Getter;

import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.FilterConfiguration;

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