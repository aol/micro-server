package com.aol.micro.server.config;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

@Microserver(propertiesName = "test!", instancePropertiesName = "test2!", properties = { "hello", "world" }, entityScan = { "packages" }, springClasses = { Classes.CORE_CLASSES }, classes = { Integer.class })
public class MicroserverConfigurerTest {
	MicroserverConfigurer configurer = new MicroserverConfigurer();

	@Test
	public void properties() {
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getProperties().keySet(), hasItem("hello"));
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getProperties().values(), hasItem("world"));
	}

	@Test
	public void propertiesName() {
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getPropertiesName(), is("test!"));

	}

	@Test
	public void instancPpropertiesName() {
		assertThat(configurer.buildConfig(MicroserverConfigurerTest.class).getInstancePropertiesName(), is("test2!"));

	}

	@Test
	public void entityScan() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		assertThat(config.getDataSources().get(config.getDefaultDataSourceName()), hasItem("packages"));

	}

	@Test
	public void springClasses() {
		Config config = configurer.buildConfig(MicroserverConfigurerTest.class);
		assertThat(config.getClasses(), hasItem(Classes.CORE_CLASSES.getClasses()[0]));

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
