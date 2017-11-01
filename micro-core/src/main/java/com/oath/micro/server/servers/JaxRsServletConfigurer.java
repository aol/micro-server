package com.oath.micro.server.servers;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.PluginLoader;
import com.oath.micro.server.module.IncorrectJaxRsPluginsException;
import com.oath.micro.server.rest.RestConfiguration;
import com.oath.micro.server.servers.model.ServerData;
import cyclops.companion.Streams;
import cyclops.reactive.ReactiveSeq;

public class JaxRsServletConfigurer {
    public void addServlet(ServerData serverData, ServletContext webappContext) {

        List<RestConfiguration> restConfigList = ReactiveSeq.fromStream(PluginLoader.INSTANCE.plugins.get()
                                                                                                     .stream())
                                                            .filter(module -> module.restServletConfiguration() != null)
                                                            .map(Plugin::restServletConfiguration)
                                                            .flatMap(Streams::optionalToStream)
                                                            .toList();
        if (restConfigList.size() > 1) {
            throw new IncorrectJaxRsPluginsException(
                                                     "ERROR!  Multiple jax-rs application plugins found "
                                                             + restConfigList);
        } else if (restConfigList.size() == 0) {
            throw new IncorrectJaxRsPluginsException(
                                                     "ERROR!  No jax-rs application plugins found ");
        }

        RestConfiguration config = restConfigList.get(0);
        javax.servlet.ServletRegistration.Dynamic servletRegistration = webappContext.addServlet(config.getName() + "-"
                + serverData.getModule()
                            .getContext(), config.getServlet());
        Map<String, String> initParams = config.getInitParams();
        for (String key : initParams.keySet()) {
            servletRegistration.setInitParameter(key, initParams.get(key));
        }
        servletRegistration.setAsyncSupported(true);
        servletRegistration.setInitParameter("javax.ws.rs.Application", serverData.getModule()
                                                                                  .getJaxWsRsApplication());
        servletRegistration.setInitParameter(config.getProvidersName(), serverData.getModule()
                                                                                  .getProviders());
        servletRegistration.setInitParameter("context", serverData.getModule()
                                                                  .getContext());
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping(serverData.getBaseUrlPattern());
    }

}
