package app.filter.com.aol.micro.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cyclops.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.oath.micro.server.auto.discovery.AutoFilterConfiguration;

import lombok.Getter;
import lombok.Setter;

@Component
public class AutodiscoveredFilter  implements AutoFilterConfiguration {

	@Autowired
	Bean bean;
	@Getter
	@Setter
	private static volatile int called= 0;
	
	@Getter
	private static boolean beanSet = false;
	
	@Override
	public String[] getMapping() {
		return new String[] { "/*" };
	}
	public Either<Class<? extends Filter>,Filter> getFilter(){

		return Either.left(org.springframework.web.filter.DelegatingFilterProxy.class);

	}
	public String getName(){
		return "autodiscoveredFilter";
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		called++;
		if(bean!=null)
			beanSet =true;
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
		
	}

	

}