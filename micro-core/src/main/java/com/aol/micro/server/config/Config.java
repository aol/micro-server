package com.aol.micro.server.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.PMap;
import org.pcollections.PSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

/**
 * 
 * Class for configuring a Spring Context for Microserver
 * 
 * @author johnmcclean
 *
 */
@AllArgsConstructor
@Getter
@Wither
public class Config {

    private final String defaultDataSourceName;
    private final PSet<Class> classes;
    private final PMap<String, String> properties;

    private final String propertiesName;
    private final String instancePropertiesName;
    private final String serviceTypePropertiesName;
    private final PMap<String, List<String>> dataSources;
    private final SSLProperties sslProperties;
    private final boolean allowCircularReferences;
    private final String[] basePackages;

    public Config() {
        classes = HashTreePSet.empty();
        properties = HashTreePMap.empty();
        dataSources = HashTreePMap.empty();
        defaultDataSourceName = "db";
        propertiesName = "application.properties";
        instancePropertiesName = "instance.properties";
        serviceTypePropertiesName = "service-type.properties";
        sslProperties = null;
        allowCircularReferences = false;
        basePackages = new String[0];

    }

    private static volatile Config instance = null;

    public Config set() {
        instance = this;
        return this;
    }

    public static Config instance() {
        instance = new Config();
        return instance;
    }

    static Config get() {
        return instance;

    }

    public static void reset() {
        instance = null;

    }

    public Config withEntityScanDataSource(String dataSource, String... packages) {
        Map<String, List<String>> newMap = new HashMap<>(
                                                         dataSources);
        newMap.put(dataSource, Arrays.asList(packages));
        return this.withDataSources(HashTreePMap.from(newMap));
    }

    /**
     * Define the packages that hibernate should scan for Hibernate entities
     * Should be used in conjunction Microserver Spring Configuration classes @See Classes#HIBERNATE_CLASSES
     * 
     * @param packages Packages to scan for hibernate entities
     * @return New Config object, with configured packages
     */
    public Config withEntityScan(String... packages) {
        Map<String, List<String>> newMap = new HashMap<>(
                                                         dataSources);
        newMap.put(defaultDataSourceName, Arrays.asList(packages));
        return this.withDataSources(HashTreePMap.from(newMap));
    }

    public Config withClassesArray(Class... classes) {
        Set<Class> org = new HashSet<Class>(
                                            this.getClasses());
        for (Class c : classes)
            org.add(c);
        return this.withClasses(HashTreePSet.from(org));
    }

}
