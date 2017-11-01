package com.oath.micro.server.module;

import static com.oath.micro.server.utility.UsefulStaticMethods.concat;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import com.oath.micro.server.auto.discovery.CommonRestResource;
import com.oath.micro.server.servers.model.ServerData;

import com.oath.cyclops.types.persistent.PersistentMap;
import cyclops.collections.mutable.ListX;
import cyclops.collections.mutable.MapX;
import cyclops.collections.mutable.SetX;
import cyclops.data.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Wither;


@Builder
@AllArgsConstructor
public class ConfigurableModule implements Module {
    @Wither
    private final Set<Object> jaxRsResourceObjects;
    @Wither
    private final Set<Class<?>> restResourceClasses;
    @Wither
    private final Set<Class<? extends Annotation>> restAnnotationClasses;
    @Wither
    private final List<Class<?>> defaultResources;
    @Wither
    private final List<ServletContextListener> listeners;
    @Wither
    private final List<ServletRequestListener> requestListeners;
    @Wither
    private final Map<String, Filter> filters;
    @Wither
    private final Map<String, Servlet> servlets;
    @Wither
    private final String jaxWsRsApplication;
    @Wither
    private final String providers;
    @Wither
    private final String context;
    @Wither
    private final Set<Class<?>> springConfigurationClasses;
    @Wither
    private final Map<String, String> propertyOverrides;
    @Wither
    private final List<String> defaultJaxRsPackages;

    private final Consumer<WebServerProvider<?>> serverConfigManager;
    private final Consumer<JaxRsProvider<?>> resourceConfigManager;
    @Wither
    private final Map<String, Object> serverProperties;
    @Wither
    final boolean resetAll;


    public <T> ConfigurableModule withResourceConfigManager(Consumer<JaxRsProvider<T>> resourceConfigManager) {
        return new ConfigurableModule(jaxRsResourceObjects, restResourceClasses, restAnnotationClasses, defaultResources,
                listeners, requestListeners, filters, servlets, jaxWsRsApplication, providers,
                context, springConfigurationClasses, propertyOverrides, defaultJaxRsPackages, serverConfigManager,
                (Consumer) resourceConfigManager, serverProperties, resetAll);
    }

    public <T> ConfigurableModule withServerConfigManager(Consumer<WebServerProvider<?>> serverConfigManager) {
        return new ConfigurableModule(jaxRsResourceObjects, restResourceClasses, restAnnotationClasses, defaultResources,
                listeners, requestListeners, filters, servlets, jaxWsRsApplication, providers,
                context, springConfigurationClasses, propertyOverrides, defaultJaxRsPackages,
                (Consumer) serverConfigManager, resourceConfigManager, serverProperties, resetAll);
    }

    @Override
    public Set<Object> getJaxRsResourceObjects() {
        if (this.jaxRsResourceObjects != null)
            return SetX.fromIterable(concat(this.jaxRsResourceObjects, extract(() -> Module.super.getJaxRsResourceObjects())));
        return Module.super.getJaxRsResourceObjects();
    }

    @Override
    public <T> Consumer<WebServerProvider<T>> getServerConfigManager() {
        if (serverConfigManager != null)
            return (Consumer) serverConfigManager;

        return Module.super.getServerConfigManager();
    }

    @Override
    public <T> Consumer<JaxRsProvider<T>> getResourceConfigManager() {
        if (resourceConfigManager != null)
            return (Consumer) resourceConfigManager;

        return Module.super.getResourceConfigManager();
    }

    @Override
    public List<String> getDefaultJaxRsPackages() {
        if (defaultJaxRsPackages != null)
            return ListX.fromIterable(concat(defaultJaxRsPackages, extract(() -> Module.super.getDefaultJaxRsPackages())));

        return ListX.fromIterable(Module.super.getDefaultJaxRsPackages());
    }

    private <T> Collection<T> extract(Supplier<Collection<T>> s) {
        if (!resetAll)
            return s.get();
        return Arrays.asList();
    }

    //@TODO revert to return Map after cyclops X bug is fixed
    private <K, V> PersistentMap<K, V> extractMap(Supplier<Map<K, V>> s) {
        if (!resetAll)
            return HashMap.fromMap(s.get());
        return HashMap.empty();
    }

    @Override
    public Set<Class<?>> getRestResourceClasses() {
        if (restResourceClasses != null)
            return SetX.fromIterable(concat(restResourceClasses, extract(() -> Collections.singletonList(CommonRestResource.class))));

        return Module.super.getRestResourceClasses();
    }

    @Override
    public Set<Class<? extends Annotation>> getRestAnnotationClasses() {
        if (restAnnotationClasses != null)
            return SetX.fromIterable(concat(restAnnotationClasses, extract(() -> Module.super.getRestAnnotationClasses())));

        return Module.super.getRestAnnotationClasses();
    }

    @Override
    public List<Class<?>> getDefaultResources() {
        if (this.defaultResources != null) {
            return ListX.fromIterable((concat(this.defaultResources, extract(() -> Module.super.getDefaultResources()))));
        }

        return Module.super.getDefaultResources();
    }

    @Override
    public List<ServletContextListener> getListeners(ServerData data) {
        if (listeners != null)
            return ListX.fromIterable((concat(this.listeners, extract(() -> Module.super.getListeners(data)))));

        return Module.super.getListeners(data);
    }

    @Override
    public List<ServletRequestListener> getRequestListeners(ServerData data) {
        if (requestListeners != null)
            return ListX.fromIterable(concat(this.requestListeners,
                    extract(() -> Module.super.getRequestListeners(data))));

        return Module.super.getRequestListeners(data);
    }

    @Override
    public Map<String, Filter> getFilters(ServerData data) {
        if (filters != null)
            return MapX.fromMap(filters).plusAll(extractMap(() -> Module.super.getFilters(data)));

        return Module.super.getFilters(data);
    }

    @Override
    public Map<String, Servlet> getServlets(ServerData data) {
        if (servlets != null)
            return MapX.fromMap(servlets).plusAll(extractMap(() -> Module.super.getServlets(data)));

        return Module.super.getServlets(data);
    }

    @Override
    public String getJaxWsRsApplication() {
        if (this.jaxWsRsApplication != null)
            return jaxWsRsApplication;
        return Module.super.getJaxWsRsApplication();
    }

    @Override
    public String getProviders() {
        if (providers != null)
            return providers;
        return Module.super.getProviders();
    }

    @Override
    public String getContext() {

        return context;
    }

    @Override
    public Set<Class<?>> getSpringConfigurationClasses() {
        if (this.springConfigurationClasses != null)
            return SetX.fromIterable(concat(this.springConfigurationClasses, extract(() -> Module.super.getSpringConfigurationClasses())));

        return Module.super.getSpringConfigurationClasses();
    }

    @Override
    public Map<String, Object> getServerProperties() {
        if (serverProperties != null) {
            return MapX.fromMap(serverProperties).plusAll(extractMap(() -> Module.super.getServerProperties()));
        } else {
            return Module.super.getServerProperties();
        }
    }


}
