package com.aol.micro.server.module;

import static com.aol.micro.server.module.RestResourceTagBuilder.restResourceTags;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class EmbeddedModuleTest {

	EmbeddedModule module;
	@Before
	public void setup(){
		module = new EmbeddedModule(restResourceTags("com.aol.micro.server.module.EmbeddedModuleTest"),"test");
	}
	
	@Test
	public void testGetRestResourceClasses() {
	 assertThat(module.getRestAnnotationClasses(),hasItem(EmbeddedModuleTest.class));
	}

	@Test
	public void testGetContext() {
		 assertThat(module.getRestAnnotationClasses(),hasItem(EmbeddedModuleTest.class));
	}

	

}
