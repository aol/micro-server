package com.aol.micro.server;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.aol.micro.server.spring.hibernate.ConfigureDAO;
import com.aol.micro.server.spring.hibernate.HibernateConfig;
import com.aol.micro.server.spring.hibernate.JdbcConfig;
import com.aol.micro.server.spring.hibernate.TransactionalDAO;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Builder;
import lombok.experimental.Wither;


@Builder
@AllArgsConstructor
@Getter
@Wither
public class Config {
	private final List<Class> classes;
	private final Map<String,String> properties;
	public Config withHibernateClasses(Class... c){
		 List<Class> result = Lists.<Class>newArrayList(
				 TransactionalDAO.class,ConfigureDAO.class,
				 HibernateConfig.class,JdbcConfig.class);
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(result);
	}
}
