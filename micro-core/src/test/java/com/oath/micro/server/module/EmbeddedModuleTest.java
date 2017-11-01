package com.oath.micro.server.module;

import static com.oath.micro.server.module.RestResourceTagBuilder.restAnnotations;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class EmbeddedModuleTest {

	EmbeddedModule module;
	@Before
	public void setup(){
		
		module = EmbeddedModule.annotationModule(restAnnotations(Anno.class),"test");
	}
	
	@Test
	public void testGetRestResourceClasses() {
		assertThat(module.getRestAnnotationClasses(),hasItem(EmbeddedModuleTest.Anno.class));
	}

	@Test
	public void testGetContext() {
		 assertThat(module.getRestAnnotationClasses(),hasItem(EmbeddedModuleTest.Anno.class));
	}

	static @interface Anno{
		
	}
	

}
