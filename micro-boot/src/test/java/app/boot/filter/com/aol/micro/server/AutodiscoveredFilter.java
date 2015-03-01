package app.boot.filter.com.aol.micro.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.Getter;

import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.FilterConfiguration;

@Component
public class AutodiscoveredFilter  implements Filter, FilterConfiguration {

	@Getter
	private static volatile int called= 0;
	
	@Override
	public String[] getMapping() {
		return new String[] { "/*" };
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		called++;
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
		
	}

	

}