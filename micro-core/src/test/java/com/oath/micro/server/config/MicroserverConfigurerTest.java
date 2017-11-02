package com.oath.micro.server.config;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import cyclops.data.tuple.Tuple2;
import org.junit.Test;

import java.util.Arrays;

@Microserver(propertiesName = "test!", instancePropertiesName = "test2!",serviceTypePropertiesName = "servicetType!", 
			properties = { "hello", "world" }, entityScan = { "packages" }, classes = { String.class, Integer.class }, 
			blacklistedClasses = {String.class})

public class MicroserverConfigurerTest {
	MicroserverConfigurer configurer = new MicroserverConfigurer();

	@Test
	public void properties() {
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getProperties().stream().map(Tuple2::_1).toSet(), hasItem("hello"));
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getProperties().stream().map(Tuple2::_2).toSet(), hasItem("world"));
	}

	@Test
	public void propertiesName() {
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getPropertiesName(), equalTo("test!"));

	}

	@Test
	public void instancPpropertiesName() {
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getInstancePropertiesName(), equalTo("test2!"));

	}
	@Test
	public void serviceTypePropertiesName() {
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getServiceTypePropertiesName(), equalTo("servicetType!"));

	}

	@Test
	public void entityScan() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		assertThat(config.getDataSources().getOrElse(config.getDefaultDataSourceName(), Arrays.asList()), hasItem("packages"));

	}

	@Test
	public void springClasses() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		System.out.println(config.getClasses());
		assertThat(config.getClasses(), hasItem(Integer.class));

	}
	

	@Test
	public void selfClass() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		assertThat(config.getClasses(), hasItem(this.getClass()));

	}

	@Test
	public void additionalClasses() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		assertThat(config.getClasses(), hasItem(Integer.class));

	}

	@Test
	public void circularReferences() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		assertFalse(config.isAllowCircularReferences());
		config = configurer.buildConfig(MicroserverConfigurerTest.class).withAllowCircularReferences(true);

		assertTrue(config.isAllowCircularReferences());

	}

}
