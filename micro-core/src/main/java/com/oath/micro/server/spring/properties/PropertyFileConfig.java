package com.oath.micro.server.spring.properties;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.oath.micro.server.config.Config;
import com.oath.micro.server.config.ConfigAccessor;

@Configuration
public class PropertyFileConfig {

    private final static Logger logger = LoggerFactory.getLogger(PropertyFileConfig.class);

    public PropertyFileConfig() {

    }

    public PropertyFileConfig(boolean set) {
        if (set)
            new Config().set(); // make sure config instance is set
    }

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {

        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        Properties props = propertyFactory();

        configurer.setProperties(props);
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        return configurer;
    }

    @Bean
    public Properties propertyFactory() throws IOException {
        List<Resource> resources = loadPropertyResource();
        PropertiesFactoryBean factory = new PropertiesFactoryBean();
        factory.setLocations(resources.toArray(new Resource[resources.size()]));
        factory.afterPropertiesSet();
        Properties props = factory.getObject();

        new ConfigAccessor().get()
                            .getProperties()
                            .stream()
                            .forEach(e -> {
                                if (props.getProperty(e._1()) == null) {
                                    props.put(e._1(), e._2());
                                }
                            });

        System.getProperties()
              .entrySet()
              .forEach(e -> props.put(e.getKey(), e.getValue()));

        return props;
    }

    private List<Resource> loadPropertyResource() {
        List<Resource> resources = new ArrayList<>();
        String applicationPropertyFileName = new ConfigAccessor().get()
                                                                 .getPropertiesName();
        loadProperties(applicationPropertyFileName, "application").ifPresent(it -> resources.add(it));

        String serviceTypePropertyFileName = new ConfigAccessor().get()
                                                                 .getServiceTypePropertiesName();
        loadProperties(serviceTypePropertyFileName, "service-type").ifPresent(it -> resources.add(it));

        String instancePropertyFileName = new ConfigAccessor().get()
                                                              .getInstancePropertiesName();
        loadProperties(instancePropertyFileName, "instance").ifPresent(it -> resources.add(it));

        return resources;
    }

    private Optional<Resource> loadProperties(String applicationPropertyFileName, String type) {

        Optional<Resource> resource = Optional.empty();

        if (new File(
                     "./" + applicationPropertyFileName).exists()) {
            resource = Optional.of(new FileSystemResource(
                                                          new File(
                                                                   "./" + applicationPropertyFileName)));
            logger.info("./" + applicationPropertyFileName + " added");
        }

        URL urlResource = PropertyFileConfig.class.getClassLoader()
                                                  .getResource(applicationPropertyFileName);
        if (urlResource != null) {
            resource = Optional.of(new UrlResource(
                                                   urlResource));
            logger.info(applicationPropertyFileName + " added");
        }

        if (System.getProperty(type + ".env") != null) {
            URL envResource = PropertyFileConfig.class.getClassLoader()
                                                      .getResource(createEnvBasedPropertyFileName(applicationPropertyFileName));
            if (envResource != null) {
                resource = Optional.of(new UrlResource(
                                                       envResource));
                logger.info(createEnvBasedPropertyFileName(applicationPropertyFileName) + " added");
            }

        }
        if (System.getProperty(type + ".property.file") != null) {
            resource = Optional.of(new FileSystemResource(
                                                          new File(
                                                                   System.getProperty(type + ".property.file"))));
            logger.info(System.getProperty("application.property.file") + " added");

        }
        return resource;
    }

    private String createEnvBasedPropertyFileName(String applicationPropertyFileName) {
        return applicationPropertyFileName.substring(0, applicationPropertyFileName.lastIndexOf(".")) + "-"
                + System.getProperty("application.env") + ".properties";
    }

}
