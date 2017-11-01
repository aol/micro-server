package com.aol.micro.server.auto.discovery;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import cyclops.control.Either;




/*
* If creating a Plugin, create a ServletConfiguration Spring Bean rather than a Servlet Spring Bean
* This is because Spring Boot may also create a Servlet based of any Filter Beans (but exposed on all URLS)
*/
public interface ServletConfiguration {
	/**
	 * @return An array of URL mapping this servlet should target
	 */
	public String[] getMapping();
	default String getName(){
		return null;
	}
	default Map<String,String> getInitParameters(){
		return new HashMap<>();
	}
	/**
	 * Either is an eXclusive Or type. It holds one of two types, and one (and only one) must
	 * be returned. In this case either a Servlet class or a Servlet Object.
	 * 
	 * {@code
	 * <pre>
	 *     return Either.left(MyServlet.class);
	 *     
	 * </pre>
	 * }
	 * 
	 * {@code
	 * <pre>
	 *     return Either.right(new MyServlet());
	 *     
	 * </pre>
	 * }
	 * 
	 */
	Either<Class<? extends Servlet>,Servlet> getServlet();
}
