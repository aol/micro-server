package com.aol.micro.server.servers.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import com.oath.cyclops.types.persistent.PersistentList;
import cyclops.data.Seq;
import lombok.Getter;
import lombok.Builder;


import com.aol.micro.server.utility.UsefulStaticMethods;

@Getter
@Builder
public class AllData {

	private final ServerData serverData;
	private final PersistentList<FilterData> filterDataList;
	private final PersistentList<ServletData> servletDataList;
	private final PersistentList<ServletContextListener> servletContextListeners;
	private final PersistentList<ServletRequestListener> servletRequestListeners;

	public AllData(ServerData serverData, List<FilterData> filterDataList,
				List<ServletData> servletDataList,
				List<ServletContextListener> servletContextListeners,
				List<ServletRequestListener> servletRequestListeners  ) {

		this.servletContextListeners = Seq.fromIterable(UsefulStaticMethods.either(servletContextListeners, new ArrayList<ServletContextListener>()));

		this.servletRequestListeners = Seq.fromIterable(UsefulStaticMethods.either(servletRequestListeners, new ArrayList<ServletRequestListener>()));

		this.filterDataList = Seq.fromIterable(UsefulStaticMethods.either(filterDataList, new ArrayList<FilterData>()));
		this.servletDataList = Seq.fromIterable(UsefulStaticMethods.either(servletDataList, new ArrayList<ServletData>()));
		this.serverData = serverData;
	}
	public AllData(ServerData serverData, PersistentList<FilterData> filterDataList,
                   PersistentList<ServletData> servletDataList,
                   PersistentList<ServletContextListener> servletContextListeners,
                   PersistentList<ServletRequestListener> servletRequestListeners  ) {

		this.servletContextListeners = Seq.fromIterable(UsefulStaticMethods.either(servletContextListeners, new ArrayList<ServletContextListener>()));

		this.servletRequestListeners = Seq.fromIterable(UsefulStaticMethods.either(servletRequestListeners, new ArrayList<ServletRequestListener>()));

		this.filterDataList = Seq.fromIterable(UsefulStaticMethods.either(filterDataList, new ArrayList<FilterData>()));
		this.servletDataList = Seq.fromIterable(UsefulStaticMethods.either(servletDataList, new ArrayList<ServletData>()));
		this.serverData = serverData;
	}

}

