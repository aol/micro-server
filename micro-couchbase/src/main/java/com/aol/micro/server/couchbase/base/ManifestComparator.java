package com.aol.micro.server.couchbase.base;

import java.util.Date;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aol.cyclops.invokedynamic.ExceptionSoftener;
import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.couchbase.DistributedMapClient;
import com.aol.micro.server.rest.jackson.JacksonUtil;

/**
 * Manifest comparator for use with a distributed map -assumes single producer / multiple consumers
 * 
 * Uses to entries in the map
 * 
 * key : versioned key
 * versioned key : actual data
 * 
 * ManifestComparator stores the current version number, only when the version changes is the full
 * data set loaded from the remote store.
 * 
 * Usage as a Spring Bean - inject into the host class, and use withKey to customise for the targeted Key.
 * 
 * 
 * <pre>
 * {@code 
 * @Rest
	public class MyDataService {
	

	
	private final ManifestComparator<DataType> comparator;
	@Autowired
	public  MyDataService(ManifestComparator comparator) {
		this.comparator = comparator.withKey("test-key");
	}
 * 
 * }
 * </pre>
 * 
 * micro-couchbase configures a single ManifestComparator bean that can be customized for multiple different keys via
 * withKey
 * 
 * When your bean is injected save via saveAndIncrement, and periodically call load() to refresh data if (and only if)
 * it has changed.
 * 
 * ManifestComparator will automatically remove old versions on saveAndIncrement, but system outages may occasionally cause old keys
 * to linger, you can also use clean & cleanAll to periodically to remove old key versions.
 * 
 * 
 * @author johnmcclean
 *
 * @param <T>
 */
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public class ManifestComparator<T> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Wither
	private final String key;

	@Getter
	private volatile T data;

	@Getter
	private volatile String versionedKey;
	private final DistributedMapClient connection;

	/**
	 * Create a ManifestComparator with the supplied distributed map client
	 * Data  stored by ManifestComparator will be
	 * 
	 * key : versioned key
	 * versioned key : actual data
	 * @param connection DistributedMapClient to store comparison data
	 */
	public ManifestComparator(DistributedMapClient connection) {
		this.key = "default";
		this.versionedKey = newKey(1L).toJson();
		this.connection = connection;
	}
	/**
	 * Create a ManifestComparator with the supplied distributed map client
	 * 
	 * Data  stored by ManifestComparator will be
	 * 
	 * key : versioned key
	 * versioned key : actual data
	 * 
	 * @param key To store actual data with
	 * @param connection DistributeMapClient connection
	 */
	public ManifestComparator(String key,DistributedMapClient connection) {
		this.key = key;
		this.versionedKey = newKey(1L).toJson();
		this.connection = connection;
	}
	
	/**
	 * Create a new ManifestComparator with the same distributed map connection
	 * that targets a different key
	 * 
	 * @param key Key to store data with
	 * @return new ManifestComparator that targets specified key
	 */
	public <T> ManifestComparator<T> withKey(String key){
		return new ManifestComparator<>(key, connection);
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

	/**
	 * @return true - if current data is stale and needs refreshed
	 */
	public boolean isOutOfDate() {
		
		return !versionedKey.equals(loadKeyFromCouchbase().toJson());
	}

	/**
	 * Load data from remote store if stale
	 */
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
			throw ExceptionSoftener.throwSoftenedException(e);
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

	/**
	 * Clean all old (not current) versioned keys
	 */
	public void cleanAll() {
		clean(-1);
	}

	/**
	 * Clean specified number of old (not current) versioned keys)
	 * 
	 * @param numberToClean
	 */
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

	/**
	 * Save provided data with the key this ManifestComparator manages
	 * bump the versioned key version.
	 * 
	 * NB : To avoid race conditions - make sure only one service (an elected leader) can write at a time (see micro-mysql for a mysql distributed lock,
	 * or micro-curator for a curator / zookeeper distributed lock implementation).
	 * 
	 * @param data to save
	 */
	public void saveAndIncrement(T data) {
		T oldData = this.data;
		VersionedKey newVersionedKey = increment();
		logger.info( "Saving data with key {}, new version is {}", key,newVersionedKey.toJson());
		connection.put(newVersionedKey.toJson(), new Data(data, new Date(), newVersionedKey.toJson()));
		connection.put(key, newVersionedKey.toJson());
		try {
			this.data = data;
			delete(versionedKey);
			
		} catch(Throwable t){
			this.data = oldData;
		}finally {
			versionedKey = newVersionedKey.toJson();
		}
	}

}
