package com.aol.micro.server.rest.resources;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.CommonRestResource;

@Path("/manifest")
@Component
public class ManifestResource implements CommonRestResource{

	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	

	@GET
	@Produces("application/json")
	public Map<String, String> mainfest(@Context ServletContext context) {
		return getManifest(context.getResourceAsStream("/META-INF/MANIFEST.MF"));
	}

	
	public Map<String, String> getManifest(final InputStream input) {
		
		final Map<String, String> retMap = new HashMap<String, String>();
		try {
			Manifest manifest = new Manifest();
			manifest.read(input);
			final Attributes attributes = manifest.getMainAttributes();
			for (final Map.Entry attribute : attributes.entrySet()) {
				retMap.put(attribute.getKey().toString(), attribute.getValue().toString());
			}
		} catch (final Exception ex) {
			logger.error( "Failed to load manifest ", ex);
		}

		return retMap;
	}

}