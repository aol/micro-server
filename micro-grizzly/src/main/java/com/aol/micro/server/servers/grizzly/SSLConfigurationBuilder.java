package com.aol.micro.server.servers.grizzly;

import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;

import com.aol.micro.server.config.SSLProperties;

public class SSLConfigurationBuilder {

	
	public SSLEngineConfigurator build(SSLProperties sslProperties) {
		
		SSLContextConfigurator sslContext = new SSLContextConfigurator();

        sslContext.setKeyStoreFile("./keystore_server"); // contains server keypair
        sslContext.setKeyStorePass("asdfgh");
        sslContext.setTrustStoreFile("./truststore_server"); // contains client certificate
        sslContext.setTrustStorePass("asdfgh");
        
        SSLEngineConfigurator sslConf = new SSLEngineConfigurator(sslContext).setClientMode(false);
        return sslConf;
	}
}
