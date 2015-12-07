package com.aol.micro.server.rest.jackson;


import java.util.List;

import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.cyclops.invokedynamic.ExceptionSoftener;
import com.aol.micro.server.PluginLoader;
import com.aol.micro.server.jackson.JacksonMapperConfigurator;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

public final class JacksonUtil {

	
	
	private static ObjectMapper mapper = null;

	private static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);
	@Setter
	private volatile static List<JacksonMapperConfigurator>  jacksonConfigurers;

	private synchronized static ObjectMapper createMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			
			jacksonConfigurers.forEach(a-> a.accept(mapper));
			

		}
		return mapper;
	}
	public  static ObjectMapper getMapper() {
		if(mapper==null)
			return createMapper();
		return mapper;
	}

	public static String serializeToJsonFailSilently(final Object data) {
		try {
			return serializeToJson(data);
		} catch (Exception e) {
		}
		return "";
	}

	public static String serializeToJson(final Object data) {
		String jsonString = "";
		if (data == null)
			return jsonString;
		try {
			jsonString = getMapper().writeValueAsString(data);
		} catch (final Exception ex) {
			ExceptionSoftener.throwSoftenedException(ex);
		}
		return jsonString;
	}

	public static <T> T convertFromJson(final String jsonString, final Class<T> type) {
		try {

			
			return getMapper().readValue(jsonString, type);

		} catch (final Exception ex) {
			ExceptionSoftener.throwSoftenedException(ex);
		}
		return null;
	}
	public static <T> T convertFromJson(final String jsonString, final JavaType type) {
		try {

			
			return getMapper().readValue(jsonString, type);

		} catch (final Exception ex) {
			ExceptionSoftener.throwSoftenedException(ex);
		}
		return null;

	}

	public static Object serializeToJsonLogFailure(Object value) {
		try {
			return serializeToJson(value);
		} catch (Exception e) {
			logger.error( e.getMessage(), e);
		}
		return value.toString();
	}

}
