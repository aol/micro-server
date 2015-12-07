package com.aol.micro.server.rest;


import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;

import org.junit.Test;

import com.aol.micro.server.rest.jackson.JacksonUtil;


public class JacksonUtilTest {
	
	
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
	private List<String> data = new ArrayList();

	
}

