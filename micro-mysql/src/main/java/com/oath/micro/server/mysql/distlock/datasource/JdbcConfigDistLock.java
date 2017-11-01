package com.oath.micro.server.mysql.distlock.datasource;

import java.util.Properties;

import lombok.Builder;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Builder
@Component("distLockingEnv")
public class JdbcConfigDistLock {

	private final String driverClassName;
	private final String url;
	private final String username;
	private final String password;
	private final String showSql;
	private final String dialect;
	private final String ddlAuto;
	private final int maxPoolSize;

	private final Properties properties;

	@Autowired
	public JdbcConfigDistLock(@Value("${db.dist.connection.driver:${db.connection.driver}}") String driverClassName,
			@Value("${db.dist.connection.url:${db.connection.url}}") String url,
			@Value("${db.dist.connection.username:${db.connection.username}}") String username,
			@Value("${db.dist.connection.password:${db.connection.password}}") String password,
			@Value("${db.dist.connection.hibernate.showsql:false}") String showSql, @Value("${db.dist.connection.dialect:mysql}") String dialect,
			@Value("${db.dist.connection.ddl.auto:#{null}}") String ddlAuto, @Value("${db.dist.connection.max.pool.size:1}") int maxPoolSize,
			@Qualifier("propertyFactory") Properties properties) {
		this.properties = properties;
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
		this.showSql = showSql;
		this.dialect = dialect;
		this.ddlAuto = ddlAuto;
		this.maxPoolSize = maxPoolSize;

	}

}
