package com.oath.micro.server.config;

import java.util.Arrays;


import com.oath.cyclops.types.persistent.PersistentMap;
import com.oath.cyclops.types.persistent.PersistentSet;
import cyclops.data.*;
import java.util.List;
import java.util.Map;
import java.util.Set;



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
    private final PersistentSet<Class> classes;
    private final PersistentMap<String, String> properties;

    private final String propertiesName;
    private final String instancePropertiesName;
    private final String serviceTypePropertiesName;
    private final PersistentMap<String, List<String>> dataSources;
    private final boolean allowCircularReferences;
    private final String[] basePackages;

    public Config() {
        classes = HashSet.empty();
        properties = HashMap.empty();
        dataSources = HashMap.empty();
        defaultDataSourceName = "db";
        propertiesName = "application.properties";
        instancePropertiesName = "instance.properties";
        serviceTypePropertiesName = "service-type.properties";
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
        PersistentMap<String, List<String>> nm = dataSources.put(dataSource, Arrays.asList(packages));
        return this.withDataSources(nm);
    }

    /**
     * Define the packages that hibernate should scan for Hibernate entities
     * Should be used in conjunction Microserver Spring Configuration classes @See Classes#HIBERNATE_CLASSES
     * 
     * @param packages Packages to scan for hibernate entities
     * @return New Config object, with configured packages
     */
    public Config withEntityScan(String... packages) {
        return this.withDataSources(dataSources.put(defaultDataSourceName, Arrays.asList(packages)));
    }

    public Config withClassesArray(Class... classes) {
        PersistentSet<Class> org = this.classes;
        for (Class c : classes)
            org = org.plus(c);
        return this.withClasses(org);
    }

}
