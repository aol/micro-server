package com.oath.micro.server.auto.discovery;

import cyclops.control.Either;

import javax.servlet.Servlet;


public interface AutoServletConfiguration extends Servlet,ServletConfiguration{

	@Override
	default Either<Class<? extends Servlet>,Servlet> getServlet(){
		return Either.right(this);
	}
	default String getName(){
		return this.getClass().getCanonicalName();
	}
}
