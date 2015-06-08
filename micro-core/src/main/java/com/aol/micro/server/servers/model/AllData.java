package com.aol.micro.server.servers.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import lombok.Getter;
import lombok.experimental.Builder;

import com.aol.micro.server.utility.UsefulStaticMethods;
import com.google.common.collect.ImmutableList;

@Getter
@Builder
public class AllData {

	private final ServerData serverData;
	private final ImmutableList<FilterData> filterDataList;
	private final ImmutableList<ServletData> servletDataList;
	private final ImmutableList<ServletContextListener> servletContextListeners;
	private final ImmutableList<ServletRequestListener> servletRequestListeners;

	public AllData(ServerData serverData, List<FilterData> filterDataList, 
				List<ServletData> servletDataList,
				List<ServletContextListener> servletContextListeners,
				List<ServletRequestListener> servletRequestListeners  ) {

		this.servletContextListeners = ImmutableList.copyOf(UsefulStaticMethods.either(servletContextListeners, new ArrayList<ServletContextListener>()));

		this.servletRequestListeners = ImmutableList.copyOf(UsefulStaticMethods.either(servletRequestListeners, new ArrayList<ServletRequestListener>()));

		this.filterDataList = ImmutableList.copyOf(UsefulStaticMethods.either(filterDataList, new ArrayList<FilterData>()));
		this.servletDataList = ImmutableList.copyOf(UsefulStaticMethods.either(servletDataList, new ArrayList<ServletData>()));
		this.serverData = serverData;
	}

}

