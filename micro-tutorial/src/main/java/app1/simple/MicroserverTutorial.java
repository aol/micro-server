package app1.simple;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.spring.DataFunctionalModule;
import com.aol.micro.server.spring.datasource.DataSourceBuilder;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.aol.micro.server.spring.datasource.hibernate.HibernateConfig;
import com.aol.micro.server.spring.datasource.hibernate.SpringDataConfig;
import com.aol.micro.server.spring.datasource.jdbc.SQL;

@Microserver(entityScan = "app1.simple", propertiesName="tutorial.properties")
public class MicroserverTutorial {

	public static void main(String[] args){
		
		new MicroserverApp(()->"simple").run();
	
	}
	
}
