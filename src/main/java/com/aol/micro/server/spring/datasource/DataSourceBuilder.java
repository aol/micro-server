package com.aol.micro.server.spring.datasource;

import javax.annotation.Resource;
import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Builder
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceBuilder {
	@Resource
	private JdbcConfig env;

	@Bean(destroyMethod = "close", name = "dataSource")
	public DataSource dataSource() {
		return getDataSource();
	}
	private DataSource getDataSource() {
		BasicDataSource ds = new BasicDataSource();

		ds.setDriverClassName(env.getDriverClassName());
		ds.setUrl(env.getUrl());
		ds.setUsername(env.getUsername());
		ds.setPassword(env.getPassword());
		retrySetup(ds, -1);

		return ds;
	}
	private void retrySetup(BasicDataSource ds, int maxActive) {
		if (!"org.hibernate.dialect.HSQLDialect".equals(env.getDialect())) {
			ds.setTestOnBorrow(true);
			ds.setValidationQuery("SELECT 1");
			ds.setMaxActive(maxActive);
			ds.setMinEvictableIdleTimeMillis(1800000);
			ds.setTimeBetweenEvictionRunsMillis(1800000);
			ds.setNumTestsPerEvictionRun(3);
			ds.setTestWhileIdle(true);
			ds.setTestOnReturn(true);
		}
	}
}
