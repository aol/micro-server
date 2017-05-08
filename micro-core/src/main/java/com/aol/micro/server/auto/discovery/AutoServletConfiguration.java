package com.aol.micro.server.auto.discovery;

import cyclops.control.Xor;

import javax.servlet.Servlet;


public interface AutoServletConfiguration extends Servlet,ServletConfiguration{

	@Override
	default Xor<Class<? extends Servlet>,Servlet> getServlet(){
		return Xor.primary(this);
	}
	default String getName(){
		return this.getClass().getCanonicalName();
	}
}
