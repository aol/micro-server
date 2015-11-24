package com.aol.micro.server.spring.datasource.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import javax.sql.DataSource;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SQL {

	private final JdbcTemplate jdbc;

	@Autowired
	public SQL(@Qualifier("mainDataSource") DataSource dataSource) {
		jdbc = new JdbcTemplate(dataSource);
	}

}
