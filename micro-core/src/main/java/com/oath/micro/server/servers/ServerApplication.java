package com.oath.micro.server.servers;

import java.util.concurrent.CompletableFuture;

import com.oath.micro.server.config.SSLProperties;
import com.oath.micro.server.servers.model.ServerData;

public interface ServerApplication {
	void run(CompletableFuture start, JaxRsServletConfigurer jaxRsConfigurer, CompletableFuture end);
	ServerData getServerData();	
}
