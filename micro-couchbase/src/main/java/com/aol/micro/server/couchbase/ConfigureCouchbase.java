package com.aol.micro.server.couchbase;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;

@Configuration
public class ConfigureCouchbase {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Setter
	@Value("${couchbaseServers:}")
	private String couchbaseServers;

	@Value("${couchbaseBucket:couchbase_bucket}")
	private String couchbaseBucket;

	@Value("${couchbasePassword:}")
	private String couchbasePassword;

	@Setter
	@Value("${couchbaseClientEnabled:true}")
	private boolean couchbaseClientEnabled = true;

	@Value("${couchbaseClientOperationTimeout:120000}")
	private long opTimeout;

	@SuppressWarnings("rawtypes")
	@Bean(name = "persistentDistributedInMemoryCache")
	public SimpleCouchbaseClient simpleCouchbaseClient() throws IOException, URISyntaxException {
		if (couchbaseClientEnabled) {
			return new SimpleCouchbaseClient(couchbaseClient());
		} else {
			return new SimpleCouchbaseClient(null);
		}
	}

	@Bean(name = "persistentCouchbaseClient")
	public CouchbaseClient couchbaseClient() throws IOException, URISyntaxException {
		if (couchbaseClientEnabled) {
			logger.info("Creating CouchbaseClient for servers: {}", couchbaseServers);
			CouchbaseConnectionFactoryBuilder builder = new CouchbaseConnectionFactoryBuilder();
			builder.setOpTimeout(opTimeout);
			CouchbaseConnectionFactory cf = builder.buildCouchbaseConnection(getServersList(), couchbaseBucket,
					StringUtils.trimAllWhitespace(Optional.ofNullable(couchbasePassword).orElse("")));
			return new CouchbaseClient(cf);
		}
		return null;

	}

	private List<URI> getServersList() throws URISyntaxException {
		List<URI> uris = new ArrayList<URI>();
		for (String serverHost : StringUtils.split(couchbaseServers, ",")) {
			uris.add(new URI(serverHost));
		}
		return uris;
	}

}
