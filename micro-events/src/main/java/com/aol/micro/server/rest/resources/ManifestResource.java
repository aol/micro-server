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
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.CommonRestResource;
import com.aol.micro.server.auto.discovery.SingletonRestResource;
import com.aol.micro.server.reactive.Reactive;

@Path("/manifest")
@Component
public class ManifestResource implements CommonRestResource, SingletonRestResource,  Reactive{

	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	

	@GET
	@Produces("application/json")
	public void mainfest(@Suspended AsyncResponse asyncResponse, @Context ServletContext context) {
		
		this.ioStream().of("/META-INF/MANIFEST.MF")
					.map(url->context.getResourceAsStream(url))
					.map(this::getManifest)
					.peek(result->asyncResponse.resume(result))
					.run();
		
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