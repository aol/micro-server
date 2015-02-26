package com.aol.micro.server.servers.model;

import javax.servlet.Servlet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServletData {

		
		private final String servletName;
		private final Class<? extends Servlet> servlet;
		private final String mapping;

		
}