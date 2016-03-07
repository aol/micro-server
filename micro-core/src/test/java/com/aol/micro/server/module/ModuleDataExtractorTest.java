package com.aol.micro.server.module;

import static com.aol.micro.server.module.RestResourceTagBuilder.restResourceTags;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.cyclops.data.collections.extensions.persistent.PStackX;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.utility.HashMapBuilder;

public class ModuleDataExtractorTest {

	ModuleDataExtractor extractor;
	Module module;
	AnnotationConfigWebApplicationContext rootContext;
	ServerData data;
	Map<String, Filter> filters;
	
	@Before
	public void setup(){
		
		module = ConfigurableModule.builder()
									.restResourceClasses(restResourceTags(ModuleDataExtractorTest.class))
									.context("test")
									.servlets(HashMapBuilder.of("/*1",new BasicServlet()))
									.filters(HashMapBuilder.of("/*",new BasicFilter()))
									.build();
		extractor = new ModuleDataExtractor(module);
		rootContext = mock(AnnotationConfigWebApplicationContext.class);
		data = ServerData.builder().resources(PStackX.of()).module(module).build();
		
	}
	
	@Test
	public void testGetRestResources() {
		extractor.getRestResources(rootContext);
		verify(rootContext,times(1)).getBeansOfType(ModuleDataExtractorTest.class);
	}

	@Test
	public void testCreateFilteredDataList() {
		assertThat(extractor.createFilteredDataList(data).get(0).getMapping(),is("/*"));
	}

	@Test
	public void testCreateServletDataList() {
		assertThat(extractor.createServletDataList(data).get(0).getMapping(),is("/*1"));
	}

	public static class BasicServlet extends HttpServlet{
		
	}

}
