package com.aol.micro.server.web.cors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;


public class CrossDomainFilterTest {

	private CrossDomainFilter crossDomainFilter;

	@Before
	public void init() {
		this.crossDomainFilter = new CrossDomainFilter(true,"/*");
	}

	@Test
	public void testFilter() throws IOException, ServletException {

		ServletRequest request = mock(ServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);

		crossDomainFilter.doFilter(request, response, filterChain);

		InOrder inOrder = Mockito.inOrder(response, filterChain);

		inOrder.verify(response, times(1)).addHeader("Access-Control-Allow-Origin", "*");
		inOrder.verify(response, times(1)).addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		inOrder.verify(response, times(1)).addHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");

		inOrder.verify(filterChain, times(1)).doFilter(request, response);

	}
	@Test
	public void testFilterFalse() throws IOException, ServletException {
		this.crossDomainFilter = new CrossDomainFilter(false,"/*");
		assertTrue(this.crossDomainFilter.getMapping().length==0);
		
	}
}
