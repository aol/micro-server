package com.aol.micro.server.application.registry;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aol.cyclops.util.ExceptionSoftener;
import com.aol.micro.server.servers.ApplicationRegister;
import com.aol.micro.server.servers.model.ServerData;

@Component
public class ApplicationRegisterImpl implements ApplicationRegister {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Getter
	private volatile Application application;

	
	private final String customHostname;
	private final String targetEndpoint;
	
	@Autowired
	public ApplicationRegisterImpl(@Value("${host.address:#{null}}")String customHostname,
			                            @Value("${target.endpoint:#{null}}")String targetEndpoint){
		
		this.customHostname=customHostname;
		this.targetEndpoint = targetEndpoint;
		
	}
	
	public ApplicationRegisterImpl() {
		this(null,null);
	}

	@Override
	public void register(ServerData[] data) {
		
		try {
			final String hostname = Optional.ofNullable(customHostname).orElse( InetAddress.getLocalHost().getHostName());
			application = new Application(Stream
					.of(data)
					.map(next -> new RegisterEntry(next.getPort(), hostname, next.getModule().getContext(), next
							.getModule().getContext(), null,targetEndpoint)).collect(Collectors.toList()));
			logger.info("Registered application {} ", application);
		} catch (UnknownHostException e) {
			throw ExceptionSoftener.throwSoftenedException(e);
		}
	}
}
