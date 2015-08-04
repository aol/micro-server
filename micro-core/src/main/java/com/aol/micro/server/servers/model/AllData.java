package com.aol.micro.server.servers.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.experimental.Builder;

import org.pcollections.ConsPStack;
import org.pcollections.PStack;

import com.aol.micro.server.utility.UsefulStaticMethods;

@Getter
@Builder
public class AllData {

	private final ServerData serverData;
	private final PStack<FilterData> filterDataList;
	private final PStack<ServletData> servletDataList;
	private final PStack<ServletContextListener> servletContextListeners;
	private final PStack<ServletRequestListener> servletRequestListeners;

	public AllData(ServerData serverData, List<FilterData> filterDataList, 
				List<ServletData> servletDataList,
				List<ServletContextListener> servletContextListeners,
				List<ServletRequestListener> servletRequestListeners  ) {

		this.servletContextListeners = ConsPStack.from(UsefulStaticMethods.either(servletContextListeners, new ArrayList<ServletContextListener>()));

		this.servletRequestListeners = ConsPStack.from(UsefulStaticMethods.either(servletRequestListeners, new ArrayList<ServletRequestListener>()));

		this.filterDataList = ConsPStack.from(UsefulStaticMethods.either(filterDataList, new ArrayList<FilterData>()));
		this.servletDataList = ConsPStack.from(UsefulStaticMethods.either(servletDataList, new ArrayList<ServletData>()));
		this.serverData = serverData;
	}

}

