package com.oath.micro.server.couchbase.base;

import java.util.Date;
import java.util.Optional;

import com.oath.cyclops.util.ExceptionSoftener;
import cyclops.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.oath.micro.server.distributed.DistributedMap;
import com.oath.micro.server.manifest.Data;
import com.oath.micro.server.manifest.ManifestComparator;
import com.oath.micro.server.manifest.ManifestComparatorKeyNotFoundException;
import com.oath.micro.server.manifest.VersionedKey;
import com.oath.micro.server.rest.jackson.JacksonUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * Manifest comparator for use with a distributed map -assumes single producer /
 * multiple consumers
 * 
 * Uses to entries in the map
 * 
 * key : versioned key versioned key : actual data
 * 
 * ManifestComparator stores the current version number, only when the version
 * changes is the full data set loaded from the remote store.
 * 
 * Usage as a Spring Bean - inject into the host class, and use withKey to
 * customise for the targeted Key.
 * 
 * 
 * <pre>
 * {@code 
 * &#64;Rest
	public class MyDataService {
	

	
	private final ManifestComparator<DataType> comparator;
	&#64;Autowired
	public  MyDataService(ManifestComparator comparator) {
		this.comparator = comparator.withKey("test-key");
	}
 * 
 * }
 * </pre>
 * 
 * micro-couchbase configures a single ManifestComparator bean that can be
 * customized for multiple different keys via withKey
 * 
 * When your bean is injected save via saveAndIncrement, and periodically call
 * load() to refresh data if (and only if) it has changed.
 * 
 * ManifestComparator will automatically remove old versions on
 * saveAndIncrement, but system outages may occasionally cause old keys to
 * linger, you can also use clean & cleanAll to periodically to remove old key
 * versions.
 * 
 * 
 * @author johnmcclean
 *
 * @param <T>
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CouchbaseManifestComparator<T> implements ManifestComparator<T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String key;

    private volatile Either<Void, T> data = Either.left(null); // Void represents
                                                              // an unitialized
                                                              // state

    @Getter
    private volatile String versionedKey;
    private final DistributedMap connection;

    /**
     * Create a ManifestComparator with the supplied distributed map client Data
     * stored by ManifestComparator will be
     * 
     * key : versioned key versioned key : actual data
     * 
     * @param connection
     *            DistributedMapClient to store comparison data
     */
    public CouchbaseManifestComparator(DistributedMap connection) {
        this.key = "default";
        this.versionedKey = newKey(1L).toJson();
        this.connection = connection;
    }

    /**
     * Create a ManifestComparator with the supplied distributed map client
     * 
     * Data stored by ManifestComparator will be
     * 
     * key : versioned key versioned key : actual data
     * 
     * @param key
     *            To store actual data with
     * @param connection
     *            DistributeMapClient connection
     */
    public CouchbaseManifestComparator(String key, DistributedMap connection) {
        this.key = key;
        this.versionedKey = newKey(1L).toJson();
        this.connection = connection;
    }

    /**
     * Create a new ManifestComparator with the same distributed map connection
     * that targets a different key
     * 
     * @param key
     *            Key to store data with
     * @return new ManifestComparator that targets specified key
     */
    @Override
    public <R> CouchbaseManifestComparator<R> withKey(String key) {
        return new CouchbaseManifestComparator<>(
                                                 key, connection);
    }

    private VersionedKey newKey(Long version) {
        return new VersionedKey(
                                key, version);
    }

    private VersionedKey increment() {
        VersionedKey currentVersionedKey = loadKeyFromCouchbase();
        return currentVersionedKey.withVersion(currentVersionedKey.getVersion() + 1);
    }

    private VersionedKey loadKeyFromCouchbase() {
        Optional<String> optionalKey = connection.get(key);
        return optionalKey.flatMap(val -> Optional.of(JacksonUtil.convertFromJson(val, VersionedKey.class)))
                          .orElse(newKey(0L));

    }

    @Override
    @SneakyThrows
    public T getData() {
        while (data.isLeft()) {
            Thread.sleep(500);
        }
        return data.orElse(null);
    }

    @Override
    public T getCurrentData() {
        return data.fold(present -> present, () -> null);
    }

    /**
     * @return true - if current data is stale and needs refreshed
     */
    @Override
    public boolean isOutOfDate() {

        return !versionedKey.equals(loadKeyFromCouchbase().toJson());
    }

    /**
     * Load data from remote store if stale
     */
    @Override
    public synchronized boolean load() {
        Either<Void, T> oldData = data;
        String oldKey = versionedKey;
        try {
            if (isOutOfDate()) {
                String newVersionedKey = (String) connection.get(key)
                                                            .get();
                data = Either.right((T) nonAtomicload(newVersionedKey));
                versionedKey = newVersionedKey;
            } else {
                return false;
            }
        } catch (Throwable e) {
            data = oldData;
            versionedKey = oldKey;
            logger.debug(e.getMessage(), e);
            throw ExceptionSoftener.throwSoftenedException(e);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private Object nonAtomicload(String newVersionedKey) throws Throwable {
        Data data = (Data) connection.get(newVersionedKey)
                                     .orElseThrow(() -> {
                                         return new ManifestComparatorKeyNotFoundException(
                                                                                           "Missing versioned key "
                                                                                                   + newVersionedKey
                                                                                                   + " - likely data changed during read");
                                     });
        logger.info("Loaded new data with date {} for key {}, versionedKey {}, versionedKey from data ",
                    new Object[] { data.getDate(), key, newVersionedKey, data.getVersionedKey() });
        return data.getData();
    }

    /**
     * Clean all old (not current) versioned keys
     */
    @Override
    public void cleanAll() {
        clean(-1);
    }

    /**
     * Clean specified number of old (not current) versioned keys)
     * 
     * @param numberToClean
     */
    @Override
    public void clean(int numberToClean) {
        logger.info("Attempting to delete the last {} records for key {}", numberToClean, key);
        VersionedKey currentVersionedKey = loadKeyFromCouchbase();
        long start = 0;
        if (numberToClean != -1)
            start = currentVersionedKey.getVersion() - numberToClean;
        for (long i = start; i < currentVersionedKey.getVersion(); i++) {
            delete(currentVersionedKey.withVersion(i)
                                      .toJson());
        }
        logger.info("Finished deleting the last {} records for key {}", numberToClean, key);
    }

    private void delete(String withVersion) {
        connection.delete(withVersion);
    }

    /**
     * Save provided data with the key this ManifestComparator manages bump the
     * versioned key version.
     * 
     * NB : To avoid race conditions - make sure only one service (an elected
     * leader) can write at a time (see micro-mysql for a mysql distributed
     * lock, or micro-curator for a curator / zookeeper distributed lock
     * implementation).
     * 
     * @param data
     *            to save
     */
    @Override
    public void saveAndIncrement(T data) {
        Either<Void, T> oldData = this.data;
        VersionedKey newVersionedKey = increment();
        logger.info("Saving data with key {}, new version is {}", key, newVersionedKey.toJson());
        connection.put(newVersionedKey.toJson(), new Data(
                                                          data, new Date(), newVersionedKey.toJson()));
        connection.put(key, newVersionedKey.toJson());
        try {
            this.data = Either.right(data);
            delete(versionedKey);

        } catch (Throwable t) {
            this.data = oldData;
        } finally {
            versionedKey = newVersionedKey.toJson();
        }
    }

    @Override
    public String toString() {
        return "[CouchbaseManifestComparator:key:" + key + ",versionedKey:" + JacksonUtil.serializeToJson(versionedKey)
                + "]";
    }
}
