package nonautoscan.com.aol.micro.server;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.aol.micro.server.config.SSLProperties;

@Configuration
public class SSLConfig {

	private static String keyStoreFile = "keyStoreFile";
	private static String keyStorePass = "keyStorePass";
	private static String trustStoreFile = "trustStoreFile";
	private static String trustStorePass = "trustStorePass";
	private static String keyStoreType = "keyStoreType";
	private static String keyStoreProvider = "keyStoreProvider";
	private static String trustStoreType = "trustStoreType";
	private static String trustStoreProvider = "trustStoreProvider";
	private static String clientAuth = "clientAuth";
	private static String ciphers = "ciphers";
	private static String protocol = "protocol";

	@Bean
	public static SSLProperties sslProperties() throws IOException {
		PropertiesFactoryBean factory = new PropertiesFactoryBean();
		URL url = SSLConfig.class.getClassLoader().getResource("ssl.properties");
		if (url != null) {
			Resource reource = new UrlResource(url);
			factory.setLocation(reource);
			factory.afterPropertiesSet();
			Properties properties = factory.getObject();
			return SSLProperties.builder()
					.keyStoreFile(properties.getProperty(keyStoreFile))
					.keyStorePass(properties.getProperty(keyStorePass))
					.trustStoreFile(properties.getProperty(trustStoreFile))
					.trustStorePass(properties.getProperty(trustStorePass))
					.keyStoreType(properties.getProperty(keyStoreType))
					.keyStoreProvider(properties.getProperty(keyStoreProvider))
					.trustStoreType(properties.getProperty(trustStoreType))
					.trustStoreProvider(properties.getProperty(trustStoreProvider))
					.clientAuth(properties.getProperty(clientAuth))
					.ciphers(properties.getProperty(ciphers))
					.protocol(properties.getProperty(protocol)).build();
		}
		return null;
	}
}
