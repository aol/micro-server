package com.oath.micro.server.servers;

import org.springframework.context.ApplicationContext;

import com.oath.micro.server.module.Module;

public interface ServerApplicationFactory {

	public ServerApplication createApp(Module module,ApplicationContext springContext);
}
