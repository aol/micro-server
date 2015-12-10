package com.aol.micro.server.servers.grizzly;

import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;

import com.aol.micro.server.config.SSLProperties;

public class SSLConfigurationBuilder {

	
	public SSLEngineConfigurator build(SSLProperties sslProperties) {
		
		SSLContextConfigurator sslContext = new SSLContextConfigurator();

        sslContext.setKeyStoreFile(sslProperties.getKeyStoreFile()); // contains server keypair
        sslContext.setKeyStorePass(sslProperties.getKeyStorePass());
        sslContext.setTrustStoreFile(sslProperties.getTrustStoreFile()); // contains client certificate
        sslContext.setTrustStorePass(sslProperties.getTrustStorePass());
        
        
        
        sslProperties.getKeyStoreType().peek(type->sslContext.setKeyStoreType(type));
        sslProperties.getKeyStoreProvider().peek(provider->sslContext.setKeyStoreProvider(provider));
		
        
        sslProperties.getTrustStoreType().peek(type->sslContext.setTrustStoreType(type));
        sslProperties.getTrustStoreProvider().peek(provider->sslContext.setTrustStoreProvider(provider));
		
		
		
		
		
        SSLEngineConfigurator sslConf = new SSLEngineConfigurator(sslContext).setClientMode(false);
        sslProperties.getClientAuth().filter(auth-> auth.toLowerCase().equals("want"))
									.peek(auth->sslConf.setWantClientAuth(true));
        sslProperties.getClientAuth().filter(auth-> auth.toLowerCase().equals("need"))
							.peek(auth->sslConf.setNeedClientAuth(true));
        sslProperties.getCiphers().peek(ciphers->sslConf.setEnabledCipherSuites(ciphers.split(",")))
        			.peek(c-> sslConf.setCipherConfigured(true));
        sslProperties.getProtocol().peek(pr->sslConf.setEnabledProtocols(pr.split(",")))
        						.peek(p->sslConf.setProtocolConfigured(true));
        
        
        return sslConf;
	}
}
