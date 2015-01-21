package nonautoscan.com.aol.micro.server;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

import com.google.common.collect.Lists;

@Configuration
public class PropertyFileConfig {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Bean
	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {

		List<Resource> resources = loadPropertyResource();

		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
		Properties props = propertyFactory();
		configurer.setProperties(props);
		configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
		return configurer;
	}
	
	@Bean
	public Properties propertyFactory() throws IOException{
		List<Resource> resources = loadPropertyResource();
		PropertiesFactoryBean factory = new PropertiesFactoryBean();
		factory.setLocations(resources.toArray(new Resource[resources.size()]));
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	private List<Resource> loadPropertyResource() {
		List<Resource> resources = Lists.newArrayList();
		loadProperties().ifPresent(it -> resources.add(it));

		URL instanceResource = getClass().getClassLoader().getResource("instance.properties");
		if (instanceResource != null) {
			resources.add(new UrlResource(instanceResource));
			logger.info("instance.properties added");
		}

		
		return resources;
	}

	private Optional<Resource> loadProperties() {
		Optional<Resource> resource = Optional.empty();

		if (new File("./application.properties").exists()) {
			resource = Optional.of(new FileSystemResource(new File("./application.properties")));
			logger.info("./application.properties added");
		}

		URL urlResource = getClass().getClassLoader().getResource("application.properties");
		if (urlResource != null) {
			resource = Optional.of(new UrlResource(urlResource));
			logger.info("application.properties added");
		}

		if (System.getProperty("application.env") != null) {
			URL envResource = getClass().getClassLoader().getResource("application-" + System.getProperty("application.env") + ".properties");
			if (envResource != null) {
				resource = Optional.of(new UrlResource(envResource));
				logger.info("application-" + System.getProperty("application.env") + ".properties added");
			}

		}
		if (System.getProperty("application.property.file") != null) {
			resource = Optional.of(new FileSystemResource(new File(System.getProperty("application.property.file"))));
			logger.info(System.getProperty("application.property.file") + " added");

		}
		return resource;
	}
}
