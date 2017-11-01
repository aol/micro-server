package com.oath.micro.server.servers.tomcat;



import org.apache.coyote.http11.AbstractHttp11JsseProtocol;


import com.oath.micro.server.config.SSLProperties;

public class SSLConfigurationBuilder {

	
	public void build(AbstractHttp11JsseProtocol<?> protocol,SSLProperties sslProperties) {
	    protocol.setKeystoreFile(sslProperties.getKeyStoreFile()); // contains server keypair
        protocol.setKeyPass(sslProperties.getKeyStorePass());
        sslProperties.getKeyStoreType().ifPresent(type->protocol.setKeystoreType(type));
        sslProperties.getKeyStoreProvider().ifPresent(provider->protocol.setKeystoreProvider(provider));
		
        sslProperties.getTrustStoreFile().ifPresent(file->protocol.setTruststoreFile(file)); // contains client certificate
        sslProperties.getTrustStorePass().ifPresent(pass->protocol.setTruststorePass(pass));
        
        sslProperties.getTrustStoreType().ifPresent(type->protocol.setTruststoreType(type));
        sslProperties.getTrustStoreProvider().ifPresent(provider->protocol.setTruststoreProvider(provider));
		sslProperties.getClientAuth().ifPresent(auth->protocol.setClientAuth(auth));
		
		protocol.setSSLEnabled(true);
		sslProperties.getCiphers().ifPresent(ciphers->protocol.setCiphers(ciphers));
		sslProperties.getProtocol().ifPresent(pr->protocol.setSslProtocol(pr));
       
	
	}
}
