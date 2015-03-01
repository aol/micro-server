package com.aol.micro.server.config;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

@Microserver(propertiesName="test!", properties={"hello","world"}, entityScan={"packages"}, springClasses={Classes.CORE_CLASSES}, classes={Integer.class})
public class MicroserverConfigurerTest {
	MicroserverConfigurer configurer = new MicroserverConfigurer();
	
	@Test
	public void properties() {
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getProperties().keySet(),
				hasItem("hello"));
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getProperties().values(),
				hasItem("world"));
	}
	@Test
	public void propertiesName() {
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getPropertiesName(),
				is("test!"));
		
	}
	@Test
	public void entityScan() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		assertThat(config.getDataSources().get(config.getDefaultDataSourceName()),
				hasItem("packages"));
		
	}
	@Test
	public void springClasses() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		assertThat(config.getClasses(),
				hasItem(Classes.CORE_CLASSES.getClasses()[0]));
		
	}
	@Test
	public void selfClass() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		assertThat(config.getClasses(),
				hasItem(this.getClass()));
		
	}
	@Test
	public void additionalClasses() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		assertThat(config.getClasses(),
				hasItem(Integer.class));
		
	}
	
	

}
