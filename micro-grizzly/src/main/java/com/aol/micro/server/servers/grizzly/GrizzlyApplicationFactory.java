package com.aol.micro.server.servers.grizzly;

import java.util.List;

import com.oath.cyclops.types.persistent.PersistentList;
import lombok.AllArgsConstructor;


import org.springframework.context.ApplicationContext;

import com.aol.micro.server.module.Environment;
import com.aol.micro.server.module.Module;
import com.aol.micro.server.module.ModuleDataExtractor;
import com.aol.micro.server.servers.ServerApplication;
import com.aol.micro.server.servers.ServerApplicationFactory;
import com.aol.micro.server.servers.model.AllData;
import com.aol.micro.server.servers.model.FilterData;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.servers.model.ServletData;

@AllArgsConstructor
public class GrizzlyApplicationFactory implements ServerApplicationFactory {

	
	
	
	public ServerApplication createApp(final  Module module, final ApplicationContext rootContext) {
		 ModuleDataExtractor extractor = new ModuleDataExtractor(module);
		PersistentList resources = extractor.getRestResources(rootContext);

		Environment environment = rootContext.getBean(Environment.class);

		environment.assureModule(module);
		String fullRestResource = "/" + module.getContext() + "/*";

		ServerData serverData = new ServerData(environment.getModuleBean(module).getPort(), 
				resources,
				rootContext, fullRestResource, module);
		List<FilterData> filterDataList = extractor.createFilteredDataList(serverData);
		List<ServletData> servletDataList = extractor.createServletDataList(serverData);

		GrizzlyApplication app = new GrizzlyApplication(
				new AllData(serverData,
							filterDataList,
							servletDataList,
							module.getListeners(serverData),
							module.getRequestListeners(serverData)));
		return app;
	}
}
