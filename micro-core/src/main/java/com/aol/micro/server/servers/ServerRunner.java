package com.aol.micro.server.servers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;




import org.pcollections.ConsPStack;
import org.pcollections.PStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.module.Module;
import com.aol.micro.server.servers.model.ServerData;

public class ServerRunner {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final PStack<ServerApplication> apps;
	private final Optional<ApplicationRegister> register;
	private final CompletableFuture end;

	public ServerRunner(ApplicationRegister register, List<ServerApplication> apps, CompletableFuture end) {
		this.apps = ConsPStack.from(apps);
		this.register = Optional.of(register);
		this.end = end;
	}
	public ServerRunner(List<ServerApplication> apps, CompletableFuture end) {
		this.apps = ConsPStack.from(apps);
		this.register = Optional.empty();
		this.end = end;
	}

	public List<Thread> run() {

		register.ifPresent( reg -> 
			reg.register(
				apps.stream().map(app -> app.getServerData())
					.collect(Collectors.toList())
					.toArray(new ServerData[0])));

		Map<ServerApplication,CompletableFuture> mapFutures = new HashMap<>();
		apps.stream().forEach(app -> mapFutures.put(app,new CompletableFuture()));
		
		List<Thread> threads = apps.stream().map(app -> start(app, app.getServerData().getModule(),mapFutures.get(app))).collect(Collectors.toList());
		mapFutures.values().forEach(future -> get(future));
		
		logger.info("Started {} Rest applications ", apps.size());
		return threads;
	}

	private void get(CompletableFuture future) {
		try {
			future.get();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(),e);
		} catch (ExecutionException e) {
			logger.error(e.getMessage(),e);
		}
	}
	private Thread start(ServerApplication next, Module module, CompletableFuture start) {
		Thread t = new Thread(() -> { 
			ServerThreadLocalVariables.getContext().set(module.getContext());
			next.run(start, end); 
			
		});
		
		t.setName(module.getContext());
		t.start();
		return t;
	}

	
}
