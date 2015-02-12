package com.aol.micro.server.module;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.aol.micro.server.auto.discovery.CommonRestResource;
public class RestResourceTagBuilderTest {

	@Test(expected=ClassNotFoundException.class)
	public void testRestResourceTagNonsense() {
		RestResourceTagBuilder.restResourceTags("com.aol.micro.server.module.RestResourceTagBuilderTest","nonsense");
		fail("should not get here, ClassNotFound expected");
	}
	@Test
	public void testRestResourceTag() {
		assertThat(RestResourceTagBuilder.restResourceTags("com.aol.micro.server.module.RestResourceTagBuilderTest"), hasItem( RestResourceTagBuilderTest.class));
	}
	@Test
	public void testRestResourceTagClasses() {
		assertThat(RestResourceTagBuilder.restResourceTags(RestResourceTagBuilderTest.class), hasItem( RestResourceTagBuilderTest.class));
	}
	@Test
	public void testRestResourceTagDefaults() {
		assertThat(RestResourceTagBuilder.restResourceTags("com.aol.micro.server.module.RestResourceTagBuilderTest"), hasItem( CommonRestResource.class));
	}
	@Test
	public void testRestResourceTagClassesDefaults() {
		assertThat(RestResourceTagBuilder.restResourceTags(RestResourceTagBuilderTest.class), hasItem( CommonRestResource.class));
	}

}
