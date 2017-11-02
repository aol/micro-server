package com.oath.micro.server.servers.grizzly;

import cyclops.control.Maybe;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;

import com.oath.micro.server.config.SSLProperties;

public class SSLConfigurationBuilder {

	
	public SSLEngineConfigurator build(SSLProperties sslProperties) {
		
		SSLContextConfigurator sslContext = new SSLContextConfigurator();

        sslContext.setKeyStoreFile(sslProperties.getKeyStoreFile()); // contains server keypair
        sslContext.setKeyStorePass(sslProperties.getKeyStorePass());
        
        /**
         * trustStore stores public key or certificates from CA (Certificate Authorities) 
         * which is used to trust remote party or SSL connection. So should be optional
         */
        sslProperties.getTrustStoreFile().ifPresent(file->sslContext.setTrustStoreFile(file)); // contains client certificate
        sslProperties.getTrustStorePass().ifPresent(pass->sslContext.setTrustStorePass(pass));
        
        
        
        sslProperties.getKeyStoreType().ifPresent(type->sslContext.setKeyStoreType(type));
        sslProperties.getKeyStoreProvider().ifPresent(provider->sslContext.setKeyStoreProvider(provider));
		
        
        sslProperties.getTrustStoreType().ifPresent(type->sslContext.setTrustStoreType(type));
        sslProperties.getTrustStoreProvider().ifPresent(provider->sslContext.setTrustStoreProvider(provider));
		
		
		
		
		
        SSLEngineConfigurator sslConf = new SSLEngineConfigurator(sslContext).setClientMode(false);
        sslProperties.getClientAuth().filter(auth-> auth.toLowerCase().equals("want"))
									.ifPresent(auth->sslConf.setWantClientAuth(true));
        sslProperties.getClientAuth().filter(auth-> auth.toLowerCase().equals("need"))
							.ifPresent(auth->sslConf.setNeedClientAuth(true));
        Maybe.fromOptional(sslProperties.getCiphers()).peek(ciphers->sslConf.setEnabledCipherSuites(ciphers.split(",")))
        			.forEach(c-> sslConf.setCipherConfigured(true));
        Maybe.fromOptional(sslProperties.getProtocol()).peek(pr->sslConf.setEnabledProtocols(pr.split(",")))
        						.forEach(p->sslConf.setProtocolConfigured(true));
        
        
        return sslConf;
	}
}
