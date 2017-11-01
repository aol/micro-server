package com.aol.micro.server.servers;

import org.springframework.context.ApplicationContext;

import com.aol.micro.server.module.Module;

public interface ServerApplicationFactory {

	public ServerApplication createApp(Module module,ApplicationContext springContext);
}
