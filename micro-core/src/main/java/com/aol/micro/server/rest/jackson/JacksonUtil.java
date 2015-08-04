package com.aol.micro.server.rest.jackson;


import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.cyclops.lambda.monads.SequenceM;
import com.aol.cyclops.lambda.utils.ExceptionSoftener;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

public final class JacksonUtil {

	private final static ExceptionSoftener softener = ExceptionSoftener.singleton.factory.getInstance();
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

			
			SequenceM.fromStream(PluginLoader.INSTANCE.plugins.get().stream())
				.filter(m -> m.jacksonModules()!=null)
				.flatMapCollection(Plugin::jacksonModules)
				.forEach(m -> mapper.registerModule(m));
				
			mapper.registerModule(new Jdk8Module());

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
			softener.throwSoftenedException(ex);
		}
		return jsonString;
	}

	public static <T> T convertFromJson(final String jsonString, final Class<T> type) {
		try {

			
			return getMapper().readValue(jsonString, type);

		} catch (final Exception ex) {
			softener.throwSoftenedException(ex);
		}
		return null;
	}
	public static <T> T convertFromJson(final String jsonString, final JavaType type) {
		try {

			
			return getMapper().readValue(jsonString, type);

		} catch (final Exception ex) {
			softener.throwSoftenedException(ex);
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
