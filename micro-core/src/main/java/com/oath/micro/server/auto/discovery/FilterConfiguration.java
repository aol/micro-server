package com.oath.micro.server.auto.discovery;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import cyclops.control.Either;




/**
 * If creating a Plugin, create a FilterConfiguration Spring Bean rather than a Filter Spring Bean
 * This is because Spring Boot may also create a Filter based of any Filter Beans (but exposed on all URLS)
 * 
 * 
 * 
 * @author johnmcclean
 *
 */
public interface FilterConfiguration {
	
	/**
	 * @return An array of URL mapping this filter should target
	 */
	public String[] getMapping();
	
	
	/**
	 * Either is an eXclusive Or type. It holds one of two types, and one (and only one) must
	 * be returned. In this case either a Filter class or a Filter Object.
	 * 
	 * {@code
	 * <pre>
	 *     return Either.left(MyFilter.class);
	 *     
	 * </pre>
	 * }
	 * {@code
	 * <pre>
	 *     return Either.right(new MyFilter());
	 *     
	 * </pre>
	 * }
	 * 
	 * @return
	 */
	Either<Class<? extends Filter>,Filter> getFilter();
	
	default String getName(){
		return null;
	}
	default Map<String,String> getInitParameters(){
		return new HashMap<>();
	}
}
