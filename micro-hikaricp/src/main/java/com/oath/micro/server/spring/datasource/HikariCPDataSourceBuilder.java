package com.oath.micro.server.spring.datasource;

import javax.annotation.Resource;
import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Builder
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class HikariCPDataSourceBuilder {

	@Resource(name = "mainEnv")
	private JdbcConfig mainEnv;

	@Resource(name = "hikariCPEnv")
	private HikariCPConfig hikariCPEnv;

	@Bean(destroyMethod = "close", name = "mainDataSource")
	public DataSource mainDataSource() {
		return getDataSource();
	}

	private DataSource getDataSource() {
		HikariDataSource ds = new HikariDataSource();

		ds.setDriverClassName(mainEnv.getDriverClassName());
		ds.setJdbcUrl(mainEnv.getUrl());
		ds.setUsername(mainEnv.getUsername());
		ds.setPassword(mainEnv.getPassword());
		ds.setMaximumPoolSize(hikariCPEnv.getMaxPoolSize());
		ds.setMinimumIdle(hikariCPEnv.getMinimumIdle());
		ds.setIdleTimeout(hikariCPEnv.getIdleTimeout());

		return ds;
	}

}
