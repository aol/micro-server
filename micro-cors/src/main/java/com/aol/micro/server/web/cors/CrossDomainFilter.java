package com.aol.micro.server.web.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.FilterConfiguration;

@Component(value = "crossDomainFilter")
public class CrossDomainFilter implements Filter, FilterConfiguration {

	private final  boolean simple;
	private final String mapping;
	@Autowired
	public CrossDomainFilter(@Value("${cors.simple:false}")boolean simple,
				@Value("${cors.mapping:/*}")String mapping){
		this.simple=simple;
		this.mapping = mapping;
	}
	public CrossDomainFilter(){
		simple=true;
		mapping = "/*";
	}
	@Override
	public String[] getMapping() {
		if(simple)
			return new String[] {mapping };
		else
			return new String[0];
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}  
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		resp.addHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
