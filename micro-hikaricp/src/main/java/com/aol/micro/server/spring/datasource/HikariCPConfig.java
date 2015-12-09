package com.aol.micro.server.spring.datasource;

import lombok.Getter;
import lombok.experimental.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Builder
@Component("hikariCPEnv")
public class HikariCPConfig {

	private final int maxPoolSize;
	private final int minimumIdle;
	private final long idleTimeout;

	@Autowired
	public HikariCPConfig(@Value("${hikaricp.db.connection.max.pool.size:30}") int maxPoolSize, @Value("${hikaricp.db.connection.min.idle:2}") int minimumIdle,
			@Value("${hikaricp.db.connection.idle.timeout:1800000}") long idleTimeout) {		
		this.maxPoolSize = maxPoolSize;
		this.minimumIdle = minimumIdle;
		this.idleTimeout = idleTimeout;
	}
}
