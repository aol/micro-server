package com.aol.micro.server.servers;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;

import lombok.AllArgsConstructor;


import org.pcollections.PStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.auto.discovery.FilterConfiguration;
import com.aol.micro.server.servers.model.FilterData;
import com.aol.micro.server.servers.model.ServerData;

@AllArgsConstructor
public class FilterConfigurer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ServerData serverData;
	private final PStack<FilterData> filterData;
	
	public void addFilters(ServletContext webappContext) {

		addExplicitlyDeclaredFilters(webappContext);
		addAutoDiscoveredFilters(webappContext);

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
				.forEach(
						filter -> setInitParameters(
								webappContext.addFilter(getName(filter),
										getClass(filter)), filter)
								.addMappingForUrlPatterns(
										EnumSet.allOf(DispatcherType.class),true,
										filter.getMapping()));
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

	private Class<? extends Filter> getClass(FilterConfiguration filter) {
		if (filter.getFilter() != null)
			return filter.getFilter();
		return (Class<? extends Filter>) filter.getClass();
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
