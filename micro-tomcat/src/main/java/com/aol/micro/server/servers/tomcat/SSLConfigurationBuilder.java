package com.aol.micro.server.servers.tomcat;



import org.apache.coyote.http11.AbstractHttp11JsseProtocol;


import com.aol.micro.server.config.SSLProperties;

public class SSLConfigurationBuilder {

	
	public void build(AbstractHttp11JsseProtocol<?> protocol,SSLProperties sslProperties) {
	    protocol.setKeystoreFile(sslProperties.getKeyStoreFile()); // contains server keypair
        protocol.setKeyPass(sslProperties.getKeyStorePass());
        sslProperties.getKeyStoreType().peek(type->protocol.setKeystoreType(type));
        sslProperties.getKeyStoreProvider().peek(provider->protocol.setKeystoreProvider(provider));
		
        protocol.setTruststoreFile(sslProperties.getTrustStoreFile()); // contains client certificate
        protocol.setTruststorePass(sslProperties.getTrustStorePass());
        sslProperties.getTrustStoreType().peek(type->protocol.setTruststoreType(type));
        sslProperties.getTrustStoreProvider().peek(provider->protocol.setTruststoreProvider(provider));
		sslProperties.getClientAuth().peek(auth->protocol.setClientAuth(auth));
		
		protocol.setSSLEnabled(true);
		sslProperties.getCiphers().peek(ciphers->protocol.setCiphers(ciphers));
		sslProperties.getProtocol().peek(pr->protocol.setSslProtocol(pr));
       
	
	}
}
