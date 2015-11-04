package com.aol.micro.server.spring.datasource;

import javax.annotation.Resource;
import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Builder
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class DataDataSourceBuilder {

	@Resource(name = "mainEnv")
	private JdbcConfig env;

	@Bean(destroyMethod = "close", name = "mainDataSource")
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
