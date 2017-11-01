package com.oath.micro.server.spring.datasource;

import lombok.Getter;
import lombok.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Builder
@Component("dbcpEnv")
public class DBCPConfig {

	private final boolean testOnBorrow;
	private final String validationQuery;
	private final int maxTotal;
	private final long minEvictableIdleTime;
	private final long timeBetweenEvictionRuns;
	private final int numTestsPerEvictionRun;
	private final boolean testWhileIdle;
	private final boolean testOnReturn;

	@Autowired
	public DBCPConfig(@Value("${dbcp.db.test.on.borrow:true}") boolean testOnBorrow,
			@Value("${dbcp.db.validation.query:SELECT 1}") String validationQuery, @Value("${dbcp.db.max.total:-1}") int maxTotal,
			@Value("${dbcp.db.min.evictable.idle.time:1800000}") long minEvictableIdleTime,
			@Value("${dbcp.db.time.between.eviction.runs:1800000}") long timeBetweenEvictionRuns,
			@Value("${dbcp.db.num.tests.per.eviction.run:3}") int numTestsPerEvictionRun,
			@Value("${dbcp.db.test.while.idle:true}") boolean testWhileIdle, @Value("${dbcp.db.test.on.return:true}") boolean testOnReturn) {
		this.testOnBorrow = testOnBorrow;
		this.validationQuery = validationQuery;
		this.maxTotal = maxTotal;
		this.minEvictableIdleTime = minEvictableIdleTime;
		this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
		this.testWhileIdle = testWhileIdle;
		this.testOnReturn = testOnReturn;

	}
}
