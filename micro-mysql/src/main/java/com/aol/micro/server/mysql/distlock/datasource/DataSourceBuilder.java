package com.aol.micro.server.mysql.distlock.datasource;

import javax.annotation.Resource;
import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Builder
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceBuilder {
	
	@Resource(name="distLockingEnv")
	private JdbcConfigDistLock env;

	@Bean(destroyMethod = "close", name = "distLockingDataSource")
	public DataSource mainDataSource() {
		return getDataSource();
	}
	private DataSource getDataSource() {
		HikariDataSource ds = new HikariDataSource();

		ds.setDriverClassName(env.getDriverClassName());
		ds.setJdbcUrl(env.getUrl());
		ds.setUsername(env.getUsername());
		ds.setPassword(env.getPassword());

	

		return ds;
	}
	
}
