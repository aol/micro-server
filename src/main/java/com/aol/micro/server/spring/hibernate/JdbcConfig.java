package com.aol.micro.server.spring.hibernate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public class JdbcConfig  {

	String driverClassName;
	String url;
	String username;
	String password;
	String showSql;
	String dialect;
	String ddlAuto;

	@Autowired
	public JdbcConfig(@Value("${mdms.connection.driver:}") String driverClassName,
			@Value("${mdms.connection.url:}") String url,
			@Value("${mdms.connection.username:}") String username,
			@Value("${mdms.connection.password:}") String password,
			@Value("${mdms.connection.hibernate.showsql:false}") String showSql,
			@Value("${mdms.connection.dialect:}") String dialect,
			@Value("${mdms.connection.ddl.auto:#null}") String ddlAuto) {
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
		this.showSql = showSql;
		this.dialect = dialect;
		this.ddlAuto = ddlAuto;

	}

}
