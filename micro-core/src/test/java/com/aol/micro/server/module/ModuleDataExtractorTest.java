package com.aol.micro.server.module;

import static com.aol.micro.server.module.RestResourceTagBuilder.restResourceTags;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

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
									.servlets(ImmutableMap.of("/*1",new BasicServlet()))
									.build();
		extractor = new ModuleDataExtractor(module);
		rootContext = mock(AnnotationConfigWebApplicationContext.class);
		data = ServerData.builder().resources(ImmutableList.of()).module(module).build();
		
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
