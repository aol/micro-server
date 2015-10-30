package com.aol.micro.server.rest;


import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import javaslang.collection.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.Setter;

import org.junit.Test;

import com.aol.micro.server.rest.jackson.JacksonUtil;


public class JacksonUtilTest {
	
	@Test
	public void testGenerics(){
		String s = JacksonUtil.serializeToJson(List.of(new MyEntity()));
		List<MyEntity> list = JacksonUtil.convertFromJson(s, JacksonUtil.getMapper().getTypeFactory().constructParametricType(ImmutableList.class,MyEntity.class));
		assertThat(list.get(0),is(new MyEntity()));
		
	}
	@Test
	public void generateSampleRequest() {

		DummyQueryRequest request = new DummyQueryRequest();
		request.getData().add("blah");

		assertTrue(JacksonUtil.serializeToJson(request).contains("strData"));

	}
	
	
	@Test
	public void serialiseAndDeserialise(){
		DummyQueryRequest request = new DummyQueryRequest();
		request.getData().add("blah");
		String requestStr = (String) JacksonUtil.serializeToJsonLogFailure(request);
		DummyQueryRequest requestDeserialised = JacksonUtil.convertFromJson(requestStr, DummyQueryRequest.class);
		assertTrue(request.getData().contains("blah"));
	}
	
	
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "queryRequest")
@XmlType(name = "")
class DummyQueryRequest {

	@XmlElement(name = "strData")
	@Getter
	@Setter
	private java.util.List<String> data = new ArrayList();

	
}

