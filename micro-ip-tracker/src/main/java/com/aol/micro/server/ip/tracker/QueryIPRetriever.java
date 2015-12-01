package com.aol.micro.server.ip.tracker;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.aol.micro.server.auto.discovery.FilterConfiguration;


@Component
public class QueryIPRetriever implements FilterConfiguration,Filter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private static String ipForwardingHeader;
	
	private static String[] mappings;
	
	@Autowired
	public QueryIPRetriever(@Value("${load.balancer.ip.forwarding.header:X-LB-Client-IP}") String ipForwardingHeaderValue, 
							@Value("${ip.tracker.mappings:/*}") String[] mappingsValue){
		ipForwardingHeader = ipForwardingHeaderValue;
		mappings = mappingsValue;
	}
	public QueryIPRetriever(){
		mappings = new String[]{"/*"};
		ipForwardingHeader = "X-LB-Client-IP";
	}
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

	

	
	private Optional<String> getVipClientIP(ServletRequest request) {
		if (request instanceof HttpServletRequest) {

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;

			String xLbClientIP = httpServletRequest.getHeader("X-LB-Client-IP");
			logger.debug( "X-LB-Client-IP: " + xLbClientIP);
			if (!isBlank(xLbClientIP)) {
				return Optional.ofNullable(xLbClientIP);
			}

			String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
			logger.debug( "X-Forwarded-For: " + xForwardedFor);
			if (!isBlank(xForwardedFor)) {
				return Optional.ofNullable(xForwardedFor);
			}
			String ipForwardedFor = httpServletRequest.getHeader(this.ipForwardingHeader);
			logger.debug(this.ipForwardingHeader + ipForwardedFor);
			if (!isBlank(ipForwardedFor)) {
				return Optional.ofNullable(ipForwardedFor);
			}
		}
		return Optional.empty();
	}
	
	private boolean isBlank(final String str) {
		if (StringUtils.isEmpty(str))
			return true;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isWhitespace(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String[] getMapping() {
		return mappings;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	@Override
	public void destroy() {
		
		
	}

}
