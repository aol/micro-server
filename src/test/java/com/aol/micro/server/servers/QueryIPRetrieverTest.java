package com.aol.micro.server.servers;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class QueryIPRetrieverTest {

	private class MyFilterChain implements FilterChain {

		private QueryIPRetriever queryIPRetriever;
		private String ipAddress;

		@Override
		public void doFilter(ServletRequest request, ServletResponse response)
				throws IOException, ServletException {
			
			setIpAddress(QueryIPRetriever.getIpAddress().get());

		}

		public void setQueryIPRetriever(QueryIPRetriever queryIPRetriever) {
			this.queryIPRetriever = queryIPRetriever;
		}

		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}

	}

	@Test
	public void testDoFilter() throws IOException, ServletException {

		ServletRequest request = mock(ServletRequest.class);
		ServletResponse response = mock(ServletResponse.class);

		when(request.getRemoteAddr()).thenReturn("10.10.11.11");

		QueryIPRetriever queryIPRetriever = new QueryIPRetriever();
		MyFilterChain myFilterChain = new MyFilterChain();
		
		myFilterChain.setQueryIPRetriever(queryIPRetriever);

		queryIPRetriever.doFilter(request, response, myFilterChain);

		assertEquals("IP address not equal", "10.10.11.11",	myFilterChain.getIpAddress());
		assertNull(QueryIPRetriever.getIpAddress().get());

	}
	
	@Test
	public void testDoFilterWithXLBClientIPHeader() throws IOException, ServletException {

		HttpServletRequest request = mock(HttpServletRequest.class);
		ServletResponse response = mock(ServletResponse.class);

		when(request.getHeader("X-LB-Client-IP")).thenReturn("10.10.11.12");
		when(request.getRemoteAddr()).thenReturn("10.10.11.11");

		QueryIPRetriever queryIPRetriever = new QueryIPRetriever();
		MyFilterChain myFilterChain = new MyFilterChain();
		
		myFilterChain.setQueryIPRetriever(queryIPRetriever);

		queryIPRetriever.doFilter(request, response, myFilterChain);

		assertEquals("IP address not equal", "10.10.11.12",	myFilterChain.getIpAddress());
		assertNull(QueryIPRetriever.getIpAddress().get());

	}
	
	@Test
	public void testDoFilterXLBClientIPHeaderBlank() throws IOException, ServletException {

		HttpServletRequest request = mock(HttpServletRequest.class);
		ServletResponse response = mock(ServletResponse.class);

		when(request.getHeader("X-LB-Client-IP")).thenReturn("");
		when(request.getRemoteAddr()).thenReturn("10.10.11.11");

		QueryIPRetriever queryIPRetriever = new QueryIPRetriever();
		MyFilterChain myFilterChain = new MyFilterChain();
		
		myFilterChain.setQueryIPRetriever(queryIPRetriever);

		queryIPRetriever.doFilter(request, response, myFilterChain);

		assertEquals("IP address not equal", "10.10.11.11",	myFilterChain.getIpAddress());
		assertNull(QueryIPRetriever.getIpAddress().get());

	}
	
	@Test
	public void testDoFilterXLBClientIPHeaderNull() throws IOException, ServletException {

		HttpServletRequest request = mock(HttpServletRequest.class);
		ServletResponse response = mock(ServletResponse.class);

		when(request.getHeader("X-LB-Client-IP")).thenReturn(null);
		when(request.getRemoteAddr()).thenReturn("10.10.11.11");

		QueryIPRetriever queryIPRetriever = new QueryIPRetriever();
		MyFilterChain myFilterChain = new MyFilterChain();
		
		myFilterChain.setQueryIPRetriever(queryIPRetriever);

		queryIPRetriever.doFilter(request, response, myFilterChain);

		assertEquals("IP address not equal", "10.10.11.11",	myFilterChain.getIpAddress());
		assertNull(QueryIPRetriever.getIpAddress().get());

	}
	
	@Test
	public void testDoFilterWithXForwardedForHeader() throws IOException, ServletException {

		HttpServletRequest request = mock(HttpServletRequest.class);
		ServletResponse response = mock(ServletResponse.class);

		when(request.getHeader("X-Forwarded-For")).thenReturn("10.10.11.13");
		when(request.getRemoteAddr()).thenReturn("10.10.11.11");

		QueryIPRetriever queryIPRetriever = new QueryIPRetriever();
		MyFilterChain myFilterChain = new MyFilterChain();
		
		myFilterChain.setQueryIPRetriever(queryIPRetriever);

		queryIPRetriever.doFilter(request, response, myFilterChain);

		assertEquals("IP address not equal", "10.10.11.13",	myFilterChain.getIpAddress());
		assertNull(QueryIPRetriever.getIpAddress().get());

	}
	
	@Test
	public void testDoFilterXForwardedForHeaderBlank() throws IOException, ServletException {

		HttpServletRequest request = mock(HttpServletRequest.class);
		ServletResponse response = mock(ServletResponse.class);

		when(request.getHeader("X-Forwarded-For")).thenReturn("   ");
		when(request.getRemoteAddr()).thenReturn("10.10.11.11");

		QueryIPRetriever queryIPRetriever = new QueryIPRetriever();
		MyFilterChain myFilterChain = new MyFilterChain();
		
		myFilterChain.setQueryIPRetriever(queryIPRetriever);

		queryIPRetriever.doFilter(request, response, myFilterChain);

		assertEquals("IP address not equal", "10.10.11.11",	myFilterChain.getIpAddress());
		assertNull(QueryIPRetriever.getIpAddress().get());

	}
	
	@Test
	public void testDoFilterXForwardedForHeaderNull() throws IOException, ServletException {

		HttpServletRequest request = mock(HttpServletRequest.class);
		ServletResponse response = mock(ServletResponse.class);

		when(request.getHeader("X-Forwarded-For")).thenReturn(null);
		when(request.getRemoteAddr()).thenReturn("10.10.11.11");

		QueryIPRetriever queryIPRetriever = new QueryIPRetriever();
		MyFilterChain myFilterChain = new MyFilterChain();
		
		myFilterChain.setQueryIPRetriever(queryIPRetriever);

		queryIPRetriever.doFilter(request, response, myFilterChain);

		assertEquals("IP address not equal", "10.10.11.11",	myFilterChain.getIpAddress());
		assertNull(QueryIPRetriever.getIpAddress().get());

	}

}
