package com.aol.micro.server.spring.datasource.jdbc;

import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.experimental.Builder;
import lombok.experimental.Delegate;
import lombok.experimental.Wither;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.aol.simple.react.exceptions.ExceptionSoftener;


@Component
public class SQL {

	
	@Delegate
	private final JdbcTemplate jdbc;
	
	
	@Autowired
	public SQL(DataSource dataSource) {
		jdbc = new JdbcTemplate(dataSource);
	}

	


}
