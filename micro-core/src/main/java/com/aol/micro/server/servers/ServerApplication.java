package com.aol.micro.server.servers;

import java.util.concurrent.CompletableFuture;

import com.aol.micro.server.config.SSLProperties;
import com.aol.micro.server.servers.model.ServerData;

public interface ServerApplication {

	void run(CompletableFuture start, JaxRsServletConfigurer jaxRsConfigurer, CompletableFuture end);
	ServerData getServerData();
	ServerApplication withSSLProperties(SSLProperties sslProperties);
}
