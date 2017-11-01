package com.oath.micro.server.mysql.distlock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SmartDataSource;

public class DistributedLockServiceMySqlImplTest {
	DistributedLockServiceMySqlImpl service;
	DataSource dataSource;
	JdbcTemplate template;

	@Before
	public void setUp() throws Exception {
		service = new DistributedLockServiceMySqlImpl();

		template = mock(JdbcTemplate.class);
		service.jdbcTemplate = template;

	}

	@Test
	public void setDataSourceNull() {
		service.setSmartDataSource(null);
		assertThat(service.jdbcTemplate, is(template));
	}

	@Test
	public void setDataSource() {
		service.setSmartDataSource(mock(SmartDataSource.class));
		assertThat(service.jdbcTemplate, not(template));
	}

	@Test
	public void testTryLock() {
		when(service.jdbcTemplate.queryForObject(DistributedLockServiceMySqlImpl.GET_LOCK_TEMPLATE, new Object[]{"key1"},Integer.class)).thenReturn(
				1);
		service.tryLock("key1");
		verify(service.jdbcTemplate).queryForObject(
				DistributedLockServiceMySqlImpl.GET_LOCK_TEMPLATE, new Object[]{"key1"},Integer.class);
	}

	@Test
	public void testTryReleaseLock() {
		when(service.jdbcTemplate.queryForObject(DistributedLockServiceMySqlImpl.RELEASE_LOCK_TEMPLATE, new Object[]{"key1"},Integer.class)).thenReturn(
				1);
		service.tryReleaseLock("key1");
		verify(service.jdbcTemplate).queryForObject(
				DistributedLockServiceMySqlImpl.RELEASE_LOCK_TEMPLATE, new Object[]{"key1"},Integer.class);
	}

	@Test
	public void testExecuteScalarExpected() {
		String query = "my query";
		String key = "test";
		Integer expectedResult = 0;
		when(service.jdbcTemplate.queryForObject(query, new Object[]{key},Integer.class)).thenReturn(
				expectedResult);
		assertTrue(service.executeScalar(query, key, expectedResult));

	}

	@Test
	public void testExecuteScalarUnexpected() {
		String query = "my query";
		String key = "test";
		Integer expectedResult = 0;
		when(service.jdbcTemplate.queryForObject(query, new Object[]{key},Integer.class)).thenReturn(
				expectedResult);
		assertFalse(service.executeScalar(query, key, expectedResult + 1));

	}

}
