package com.aol.micro.server.spring.datasource;

import java.util.Properties;

import lombok.Getter;
import lombok.experimental.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aol.micro.server.config.ConfigAccessor;
import com.aol.micro.server.utility.UsefulStaticMethods;

@Getter
@Builder
@Component("mainEnv")
public class JdbcConfig {

	private final String driverClassName;
	private final String url;
	private final String username;
	private final String password;
	private final String showSql;
	private final String dialect;
	private final String ddlAuto;

	private final Properties properties;
	private final String name;
	private final String generateDdl;
	private final String initializationFile;
	
	public JdbcConfig(@Value("${db.connection.driver:}") String driverClassName, @Value("${db.connection.url:}") String url,
			@Value("${db.connection.username:}") String username, @Value("${db.connection.password:}") String password,
			@Value("${db.connection.hibernate.showsql:false}") String showSql, @Value("${db.connection.dialect:}") String dialect,
			@Value("${db.connection.ddl.auto:#{null}}") String ddlAuto) {
		this(driverClassName, url, username, password, showSql, dialect, ddlAuto, new Properties(), "db", "false", null);

	}

	@Autowired
	public JdbcConfig(@Value("${db.connection.driver:}") String driverClassName, @Value("${db.connection.url:}") String url,
			@Value("${db.connection.username:}") String username, @Value("${db.connection.password:}") String password,
			@Value("${db.connection.hibernate.showsql:false}") String showSql, @Value("${db.connection.dialect:}") String dialect,
			@Value("${db.connection.ddl.auto:#{null}}") String ddlAuto, @Qualifier("propertyFactory") Properties properties,
			@Value("${internal.not.use.microserver:#{null}}") String name, @Value("${db.connection.generate.ddl:false}") String generateDdl,
			@Value("${db.connection.ddl.init:#{null}}") String initializationFile) {
		this.properties = properties;
		this.name = UsefulStaticMethods.either(name, new ConfigAccessor().get().getDefaultDataSourceName());
		this.driverClassName = UsefulStaticMethods.either(driverClassName, extract("connection.driver"));
		this.url = UsefulStaticMethods.either(url, extract("connection.url"));
		this.username = UsefulStaticMethods.either(username, extract("connection.username"));
		this.password = UsefulStaticMethods.either(password, extract("connection.password"));
		this.showSql = UsefulStaticMethods.either(showSql, extract("connection.showsql"));
		this.dialect = UsefulStaticMethods.either(dialect, extract("connection.dialect"));
		this.ddlAuto = UsefulStaticMethods.either(ddlAuto, extract("connection.ddl.auto"));
		this.generateDdl = UsefulStaticMethods.either(generateDdl, extract("connection.generate.ddl"));	
		this.initializationFile = initializationFile;
	}

	private String extract(String suffix) {
		return properties.getProperty(name + "." + suffix);
	}

}
