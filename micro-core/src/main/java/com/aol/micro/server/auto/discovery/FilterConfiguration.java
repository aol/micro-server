package com.aol.micro.server.auto.discovery;

import java.util.Map;

import javax.servlet.Filter;

import org.pcollections.HashTreePMap;

import com.aol.cyclops.control.Xor;

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
	 * Xor is an eXclusive Or type. It holds one of two types, and one (and only one) must
	 * be returned. In this case either a Filter class or a Filter Object.
	 * 
	 * {@code
	 * <pre>
	 *     return Xor.secondary(MyFilter.class);
	 *     
	 * </pre>
	 * }
	 * {@code
	 * <pre>
	 *     return Xor.primary(new MyFilter());
	 *     
	 * </pre>
	 * }
	 * 
	 * @return
	 */
	Xor<Class<? extends Filter>,Filter> getFilter();
	
	default String getName(){
		return null;
	}
	default Map<String,String> getInitParameters(){
		return HashTreePMap.empty();
	}
}
