package com.aol.micro.server.couchbase.base;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

import com.aol.micro.server.couchbase.SimpleCouchbaseClient;
import com.aol.micro.server.rest.jackson.JacksonUtil;


public class ManifestComparator<T> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final String key;

	@Getter
	private volatile T data;

	@Getter
	private volatile String versionedKey;
	private final SimpleCouchbaseClient connection;

	public ManifestComparator(String key, SimpleCouchbaseClient connection) {
		this.key = key;
		this.versionedKey = newKey(1L).toJson();
		this.connection = connection;
	}

	private VersionedKey newKey(Long version) {
		return new VersionedKey(key, version);
	}

	private VersionedKey increment() {
		VersionedKey currentVersionedKey = loadKeyFromCouchbase();
		return currentVersionedKey.withVersion(currentVersionedKey.getVersion() + 1);
	}

	private VersionedKey loadKeyFromCouchbase() {
		Optional<String> optionalKey =  connection.get(key);
		return optionalKey.flatMap( val -> Optional.of(JacksonUtil.convertFromJson( val, VersionedKey.class)))
				.orElse( newKey(0L));
	
	}

	public boolean isOutOfDate() {
		
		return !versionedKey.equals(loadKeyFromCouchbase().toJson());
	}

	public synchronized void load() {
		T oldData = data;
		String oldKey = versionedKey;
		try {
			if (isOutOfDate()) {
				String newVersionedKey = (String) connection.get(key).get();
				data = (T) nonAtomicload(newVersionedKey);
				versionedKey = newVersionedKey;
			}
		} catch (Throwable e) {
			data = oldData;
			versionedKey = oldKey;
			logger.debug( e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private Object nonAtomicload(String newVersionedKey) throws Throwable {
		Data data = (Data) connection.get(newVersionedKey).orElseThrow(() -> {
			return new ManifestComparatorKeyNotFoundException("Missing versioned key " + newVersionedKey + " - likely data changed during read");
		});
		logger.info( "Loaded new data with date {} for key {}, versionedKey {}, versionedKey from data ",
				new Object[]{data.getDate(), key, newVersionedKey, data.getVersionedKey()});
		return data.getData();
	}

	public void cleanAll() {
		clean(-1);
	}

	public void clean(int numberToClean) {
		logger.info("Attempting to delete the last {} records for key {}",numberToClean,key);
		VersionedKey currentVersionedKey = loadKeyFromCouchbase();
		long start = 0;
		if (numberToClean != -1)
			start = currentVersionedKey.getVersion() - numberToClean;
		for (long i = start; i < currentVersionedKey.getVersion(); i++) {
			delete(currentVersionedKey.withVersion(i).toJson());
		}
		logger.info("Finished deleting the last {} records for key {}",numberToClean,key);
	}

	private void delete(String withVersion) {
		connection.delete(withVersion);
	}

	public void saveAndIncrement(Object data) {
		
		VersionedKey newVersionedKey = increment();
		logger.info( "Saving data with key {}, new version is {}", key,newVersionedKey.toJson());
		connection.put(newVersionedKey.toJson(), new Data(data, new Date(), newVersionedKey.toJson()));
		connection.put(key, newVersionedKey.toJson());
		try {
			delete(versionedKey);
		} finally {
			versionedKey = newVersionedKey.toJson();
		}
	}

}
