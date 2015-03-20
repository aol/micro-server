package com.aol.micro.server.servers.grizzly;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import lombok.AllArgsConstructor;

import org.glassfish.grizzly.servlet.FilterRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.auto.discovery.FilterConfiguration;
import com.aol.micro.server.auto.discovery.ServletConfiguration;
import com.aol.micro.server.servers.model.FilterData;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.servers.model.ServletData;
import com.google.common.collect.ImmutableList;

@AllArgsConstructor
public class FilterConfigurer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ServerData serverData;
	private final ImmutableList<FilterData> filterData;
	
	public void addFilters(WebappContext webappContext) {

		addExplicitlyDeclaredFilters(webappContext);
		addAutoDiscoveredFilters(webappContext);

	}

	private void addAutoDiscoveredFilters(WebappContext webappContext) {
		serverData
				.getRootContext()
				.getBeansOfType(FilterConfiguration.class)
				.values()
				.stream()
				.peek(this::logFilter)
				.forEach(
						filter -> setInitParameters(
								webappContext.addFilter(getName(filter),
										getClass(filter)), filter)
								.addMappingForUrlPatterns(
										EnumSet.allOf(DispatcherType.class),
										filter.getMapping()));
	}

	private void addExplicitlyDeclaredFilters(WebappContext webappContext) {
		for (FilterData filterData : filterData) {
			FilterRegistration filterReg = webappContext.addFilter(
					filterData.getFilterName(), filterData.getFilter());
			filterReg.addMappingForUrlPatterns(
					EnumSet.allOf(DispatcherType.class),
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

	private FilterRegistration setInitParameters(FilterRegistration addFilter,
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
