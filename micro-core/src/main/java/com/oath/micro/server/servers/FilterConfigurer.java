package com.oath.micro.server.servers;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;

import com.oath.cyclops.types.persistent.PersistentList;
import lombok.AllArgsConstructor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oath.micro.server.auto.discovery.FilterConfiguration;
import com.oath.micro.server.servers.model.FilterData;
import com.oath.micro.server.servers.model.ServerData;

@AllArgsConstructor
public class FilterConfigurer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ServerData serverData;
	private final PersistentList<FilterData> filterData;
	
	public void addFilters(ServletContext webappContext) {

		addExplicitlyDeclaredFilters(webappContext);
		addAutoDiscoveredFilters(webappContext);

	}

	private void handleFilter(FilterConfiguration filter,ServletContext webappContext){
		filter.getFilter().fold(clazz-> {
			setInitParameters(webappContext.addFilter(getName(filter),
						clazz), filter)
				.addMappingForUrlPatterns(
						EnumSet.allOf(DispatcherType.class),true,
						filter.getMapping());
			return 1; 
			}, obj-> {
							Dynamic filterReg = webappContext.addFilter(
									getName(filter), obj);
							
							filterReg.addMappingForUrlPatterns(
									EnumSet.allOf(DispatcherType.class),true,
									filter.getMapping());
							
							return 2;
						});
	}
	private void addAutoDiscoveredFilters(ServletContext webappContext) {
		serverData
				.getRootContext()
				.getBeansOfType(FilterConfiguration.class)
				.values()
				.stream()
				.filter(f->f.getMapping()!=null)
				.filter(f->f.getMapping().length>0)
				.peek(this::logFilter)
				.forEach(config->handleFilter(config,webappContext));

	}

	private void addExplicitlyDeclaredFilters(ServletContext webappContext) {
		for (FilterData filterData : filterData) {
			Dynamic filterReg = webappContext.addFilter(
					filterData.getFilterName(), filterData.getFilter());
			
			filterReg.addMappingForUrlPatterns(
					EnumSet.allOf(DispatcherType.class),true,
					filterData.getMapping());
			logFilter(filterData);
		}
	}
	private void logFilter(FilterData filter) {
		logger.info("Registering {} filter on {}",filter.getFilter().getClass().getName(), filter.getMapping());
		
	}

	private void logFilter(FilterConfiguration filter) {
		
		logger.info("Registering {} filter on {}",filter.getClass().getName(),filter.getMapping()[0]);
	}

	

	private Dynamic setInitParameters(Dynamic addFilter,
			FilterConfiguration filter) {
		addFilter.setInitParameters(filter.getInitParameters());
		return addFilter;
	}

	private String getName(FilterConfiguration filter) {
		if (filter.getName() != null)
			return filter.getName();
		return filter.getClass().getName();
	}
	
}
