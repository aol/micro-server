package com.aol.micro.server;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Builder;
import lombok.experimental.Wither;

import com.aol.micro.server.spring.datasource.DataSourceBuilder;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.aol.micro.server.spring.datasource.hibernate.DAOProvider;
import com.aol.micro.server.spring.datasource.hibernate.HibernateConfig;
import com.aol.micro.server.spring.datasource.hibernate.TransactionalDAO;
import com.aol.micro.server.spring.datasource.jdbc.SQL;
import com.google.common.collect.Lists;


@Builder
@AllArgsConstructor
@Getter
@Wither
public class Config {
	
	private static ThreadLocal<Config> instance= new ThreadLocal<>();
	
	public void set(){
		instance.set(this);
	}
	public static Config get(){
		return instance.get();
	}
	
	private final List<Class> classes;
	private final Map<String,String> properties;
	private final Map<String,DataSource> dataSources;
	private final Map<String,List<String>> dataSourcePackages;
	
	
	
	
	
	public Config withDAOClasses(Class... c){
		 List<Class> result = Lists.<Class>newArrayList(HibernateConfig.class,JdbcConfig.class ,
				 TransactionalDAO.class,DAOProvider.class,DataSourceBuilder.class,SQL.class);
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(result);
	}
}
