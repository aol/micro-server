package com.aol.micro.server.spring.boot.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.aol.cyclops.data.collections.extensions.persistent.PStackX;
import com.aol.micro.server.module.Module;
import com.aol.micro.server.module.ModuleDataExtractor;
import com.aol.micro.server.servers.model.ServerData;

public class DelegatingContextListener implements ServletContextListener{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	PStackX<ServletContextListener> listeners;
	@Autowired(required=false)
	public DelegatingContextListener(ApplicationContext c){
		this(c,()->"");
	}
	@Autowired(required=false)
	public DelegatingContextListener(ApplicationContext c,Module m){
		ModuleDataExtractor ex = new ModuleDataExtractor(m);
		PStackX res = ex.getRestResources(c);
		String fullResource = "/" + m.getContext() + "/*";
		listeners =m.getListeners(ServerData.builder().resources(res)
							.module(m).rootContext(c).baseUrlPattern(fullResource).build())
						.filter(i->!(i instanceof ContextLoader));
	}
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if(listeners==null)
			return;
		listeners.stream().forEachWithError(l->l.contextInitialized(sce),
				e->logger.error(e.getMessage(),e));
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if(listeners==null)
			return;
		listeners.stream()
				.forEachWithError(l->l.contextDestroyed(sce),
						e->logger.error(e.getMessage(),e));

		
	}

}
