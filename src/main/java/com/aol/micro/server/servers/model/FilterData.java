package com.aol.micro.server.servers.model;

import javax.servlet.Filter;

import lombok.Getter;

@Getter
public class FilterData {

	private final String mapping;
	private final String filterName;
	private final Filter filter;

	public FilterData(String mapping, String filterName, Filter filter) {
		this.mapping = mapping;
		this.filterName = filterName;
		this.filter = filter;
	}

}