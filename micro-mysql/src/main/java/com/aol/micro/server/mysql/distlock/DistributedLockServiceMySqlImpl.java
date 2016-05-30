package com.aol.micro.server.mysql.distlock;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.aol.micro.server.distlock.DistributedLockService;

@Component
public class DistributedLockServiceMySqlImpl implements DistributedLockService {

	static final String GET_LOCK_TEMPLATE = "select COALESCE(GET_LOCK(?, 0), 0)";
	static final String RELEASE_LOCK_TEMPLATE = "select COALESCE(RELEASE_LOCK(?), 0)";

	volatile JdbcTemplate jdbcTemplate;

	@Autowired(required = false)
	@Qualifier("distLockingDataSource")
	public void setSmartDataSource(DataSource dataSource) {
		if (dataSource != null)
			this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean tryLock(String key) {
		return executeScalar(GET_LOCK_TEMPLATE, key, 1);
	}

	@Override
	public boolean tryReleaseLock(String key) {
		return executeScalar(RELEASE_LOCK_TEMPLATE, key, 1);
	}

	synchronized boolean executeScalar(String operation, String key, Integer expectedResult) {

		int result = jdbcTemplate.queryForObject(operation, new Object[]{key},Integer.class);
		return result == expectedResult;

	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
