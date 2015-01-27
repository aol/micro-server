package com.aol.micro.server.servers.grizzly;

import java.util.List;

import lombok.AllArgsConstructor;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.module.Environment;
import com.aol.micro.server.module.Module;
import com.aol.micro.server.module.ModuleDataExtractor;
import com.aol.micro.server.servers.ServerApplication;
import com.aol.micro.server.servers.model.FilterData;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.servers.model.ServletData;
import com.google.common.collect.ImmutableList;

@AllArgsConstructor
public class GrizzlyApplicationFactory {

	private final AnnotationConfigWebApplicationContext rootContext;
	private final Module module;
	
	
	
	public ServerApplication createApp() {
		 ModuleDataExtractor extractor = new ModuleDataExtractor(module);
		ImmutableList<RestResource> resources = extractor.getRestResources(rootContext);

		Environment environment = rootContext.getBean(Environment.class);

		environment.assureModule(module);
		String fullRestResource = "/" + module.getContext() + "/*";

		List<FilterData> filterDataList = extractor.createFilteredDataList();
		List<ServletData> servletDataList = extractor.createServletDataList();

		GrizzlyApplication app = new GrizzlyApplication(new ServerData(environment.getModuleBean(module).getPort(), 
				filterDataList, servletDataList,resources,
				rootContext, fullRestResource, module));
		return app;
	}
}
