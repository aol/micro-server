package com.aol.micro.server.auto.discovery;

import java.util.Map;

import javax.servlet.Servlet;

import org.pcollections.HashTreePMap;

import com.aol.cyclops.control.Xor;


public interface ServletConfiguration {
	/**
	 * @return An array of URL mapping this servlet should target
	 */
	public String[] getMapping();
	default String getName(){
		return null;
	}
	default Map<String,String> getInitParameters(){
		return HashTreePMap.empty();
	}
	/**
	 * Xor is an eXclusive Or type. It holds one of two types, and one (and only one) must
	 * be returned. In this case either a Servlet class or a Servlet Object.
	 * 
	 * {@code
	 * <pre>
	 *     return Xor.secondary(MyServlet.class);
	 *     
	 * </pre>
	 * }
	 * 
	 * {@code
	 * <pre>
	 *     return Xor.primary(new MyServlet());
	 *     
	 * </pre>
	 * }
	 * 
	 */
	Xor<Class<? extends Servlet>,Servlet> getServlet();
}
