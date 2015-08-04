package com.aol.micro.server;

import org.springframework.context.ApplicationContext;

import com.aol.micro.server.module.Module;
import com.aol.micro.server.servers.ServerApplication;

public interface ServerApplicationFactory {

	public ServerApplication createApp(Module module,ApplicationContext springContext);
}
