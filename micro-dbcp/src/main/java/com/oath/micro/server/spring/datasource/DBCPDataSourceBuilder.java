package com.oath.micro.server.spring.datasource;

import javax.annotation.Resource;
import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Builder
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class DBCPDataSourceBuilder {

	@Resource(name = "mainEnv")
	private JdbcConfig mainEnv;

	@Resource(name = "dbcpEnv")
	private DBCPConfig dbcpEnv;

	@Bean(destroyMethod = "close", name = "mainDataSource")
	public DataSource mainDataSource() {
		return getDataSource();
	}

	private DataSource getDataSource() {
		BasicDataSource ds = new BasicDataSource();

		ds.setDriverClassName(mainEnv.getDriverClassName());
		ds.setUrl(mainEnv.getUrl());
		ds.setUsername(mainEnv.getUsername());
		ds.setPassword(mainEnv.getPassword());

		retrySetup(ds);

		return ds;
	}

	private void retrySetup(BasicDataSource ds) {
		if (!"org.hibernate.dialect.HSQLDialect".equals(mainEnv.getDialect())) {
			ds.setTestOnBorrow(dbcpEnv.isTestOnBorrow());
			ds.setValidationQuery(dbcpEnv.getValidationQuery());
			ds.setMaxTotal(dbcpEnv.getMaxTotal());
			ds.setMinEvictableIdleTimeMillis(dbcpEnv.getMinEvictableIdleTime());
			ds.setTimeBetweenEvictionRunsMillis(dbcpEnv.getTimeBetweenEvictionRuns());
			ds.setNumTestsPerEvictionRun(dbcpEnv.getNumTestsPerEvictionRun());
			ds.setTestWhileIdle(dbcpEnv.isTestWhileIdle());
			ds.setTestOnReturn(dbcpEnv.isTestOnReturn());
		}
	}


}
