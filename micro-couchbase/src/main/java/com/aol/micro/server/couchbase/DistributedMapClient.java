package com.aol.micro.server.couchbase;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.cyclops.util.ExceptionSoftener;
import com.couchbase.client.CouchbaseClient;

public class DistributedMapClient<V> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Optional<CouchbaseClient> couchbaseClient;

	public DistributedMapClient(CouchbaseClient couchbaseClient) {

		this.couchbaseClient = Optional.ofNullable(couchbaseClient);
	}

	
	public boolean put(final String key, final V value) {
		logger.debug("put '{}', value:{}", key, value);
		return couchbaseClient.map(c->putInternal(c,key,value)).orElse(false);

	}
	private boolean putInternal(final CouchbaseClient client, final String key, final V value){
	
		try{
			return client.set(key, value).get();
		} catch (InterruptedException | ExecutionException e) {
			throw ExceptionSoftener.throwSoftenedException(e);
			
		}
	}
	

	
	public Optional<V> get(String key) {
		return couchbaseClient.map(c->(V)c.get(key));
			
	}

	public void delete(String key) {
		couchbaseClient.map(c->c.delete(key));
	}
}
