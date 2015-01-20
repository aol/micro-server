package com.aol.micro.server.servers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.Module;
import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.ImmutableList;

public class ServerRunner {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ImmutableList<ServerApplication> apps;
	private final Optional<ApplicationRegister> register;

	public ServerRunner(Optional<ApplicationRegister> register, List<ServerApplication> apps) {
		this.apps = ImmutableList.copyOf(apps);
		this.register = register;
	}

	public void run() {

		register.ifPresent( reg -> 
			reg.register(
				apps.stream().map(app -> app.getServerData())
					.collect(Collectors.toList())
					.toArray(new ServerData[0])));

		apps.stream().map(app -> start(app, app.getServerData().getModule())).collect(Collectors.toList()).forEach(thread -> join(thread));

		logger.info("{} Rest applications started ", apps.size());
	}

	private Thread start(ServerApplication next, Module module) {
		Thread t = new Thread(() -> next.run());
		t.setName(module.getContext());
		t.start();
		return t;
	}

	private void join(Thread thread) {
		try {
			thread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}
}
