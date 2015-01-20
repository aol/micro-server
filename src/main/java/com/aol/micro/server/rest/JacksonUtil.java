package com.aol.micro.server.rest;


import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

public final class JacksonUtil {

	private static ObjectMapper mapper = null;

	private static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);
	@Setter
	private static volatile boolean strict = false;

	public synchronized static ObjectMapper getMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			if (strict)
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			JaxbAnnotationModule module = new JaxbAnnotationModule();
			// configure as necessary
			mapper.registerModule(module);

			mapper.registerModule(new GuavaModule());

		}
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
			throw new RuntimeException(ex.getMessage(), ex);
		}
		return jsonString;
	}

	public static <T> T convertFromJson(final String jsonString, final Class<T> type) {
		try {

			return getMapper().readValue(jsonString, type);

		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}

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
