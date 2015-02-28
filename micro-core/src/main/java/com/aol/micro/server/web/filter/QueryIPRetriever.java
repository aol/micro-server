package com.aol.micro.server.web.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QueryIPRetriever implements Filter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private static final ThreadLocal<String> ipAddress = new ThreadLocal<String>();

	public static String getIpAddress(){
		return ipAddress.get();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			Optional<String> vipClientIP = getVipClientIP(request);
			if (vipClientIP.isPresent()) {
				ipAddress.set(vipClientIP.get());
			} else {
				String remoteAddr = request.getRemoteAddr();
				logger.debug( "remoteAddr: " + remoteAddr);
				ipAddress.set(remoteAddr);
			}
			chain.doFilter(request, response);
		} finally {
			ipAddress.remove();
		}
	}

	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	private Optional<String> getVipClientIP(ServletRequest request) {
		if (request instanceof HttpServletRequest) {

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;

			String xLbClientIP = httpServletRequest.getHeader("X-LB-Client-IP");
			logger.debug( "X-LB-Client-IP: " + xLbClientIP);
			if (!StringUtils.isBlank(xLbClientIP)) {
				return Optional.ofNullable(xLbClientIP);
			}

			String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
			logger.debug( "X-Forwarded-For: " + xForwardedFor);
			if (!StringUtils.isBlank(xForwardedFor)) {
				return Optional.ofNullable(xForwardedFor);
			}
		}
		return Optional.empty();
	}

}
