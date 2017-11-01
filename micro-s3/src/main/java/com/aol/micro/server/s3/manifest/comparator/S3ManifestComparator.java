package com.aol.micro.server.s3.manifest.comparator;

import java.util.Date;

import com.oath.cyclops.util.ExceptionSoftener;
import cyclops.control.Try;
import cyclops.control.Either;
import cyclops.data.tuple.Tuple;
import cyclops.data.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.aol.micro.server.manifest.Data;
import com.aol.micro.server.manifest.ManifestComparator;
import com.aol.micro.server.manifest.ManifestComparatorKeyNotFoundException;
import com.aol.micro.server.manifest.VersionedKey;
import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.aol.micro.server.s3.data.S3Deleter;
import com.aol.micro.server.s3.data.S3ObjectWriter;
import com.aol.micro.server.s3.data.S3Reader;
import com.aol.micro.server.s3.data.S3StringWriter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import lombok.experimental.Wither;

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
 *  
    {@literal @}Rest
	public class MyDataService {
	
    private final ManifestComparator<DataType> comparator;
	   
	{@literal @}Autowired
	public  MyDataService(ManifestComparator comparator) {
		this.comparator = comparator.withKey("test-key");
	}
 * 
 *  }
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
 * linger, you can also use clean and cleanAll to periodically to remove old key
 * versions.
 * 
 * 
 * @author johnmcclean
 *
 * @param <T>
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class S3ManifestComparator<T> implements ManifestComparator<T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String key;

    private volatile Either<Void, T> data = Either.left(null);
    private volatile long modified = -1;

    @Getter
    private volatile String versionedKey;
    private final S3Reader reader;
    private final S3ObjectWriter writer;
    private final S3StringWriter stringWriter;
    private final S3Deleter deleter;

    @Wither
    private long backoff;

    /**
     * Create a ManifestComparator with the supplied distributed map client Data
     * stored by ManifestComparator will be
     * 
     * key : versioned key versioned key : actual data
     * 
     * @param connection
     *            DistributedMapClient to store comparison data
     */
    public S3ManifestComparator(S3Reader connection, S3ObjectWriter writer, S3Deleter deleter,
            S3StringWriter stringWriter) {
        this.key = "default";
        this.versionedKey = newKey(1L).toJson();
        this.reader = connection;
        this.writer = writer;
        this.deleter = deleter;
        this.stringWriter = stringWriter;
        backoff = 500l;
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
        return data.visit(present -> present, () -> null);
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
    public S3ManifestComparator(String key, S3Reader connection, S3ObjectWriter writer, S3Deleter deleter,
            S3StringWriter stringWriter) {
        this.key = key;
        this.versionedKey = newKey(1L).toJson();
        this.reader = connection;
        this.writer = writer;
        this.deleter = deleter;
        this.stringWriter = stringWriter;
        backoff = 500l;
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
    public <R> S3ManifestComparator<R> withKey(String key) {
        return new S3ManifestComparator<>(
                                          key, reader, writer, deleter, stringWriter);
    }

    private VersionedKey newKey(Long version) {
        return new VersionedKey(
                                key, version);
    }

    private VersionedKey increment() {
        VersionedKey currentVersionedKey = loadKeyFromS3();
        return currentVersionedKey.withVersion(currentVersionedKey.getVersion() + 1);
    }

    private VersionedKey loadKeyFromS3() {
        Try<String, Throwable> optionalKey = reader.getAsString(key);
        return optionalKey.flatMap(val -> Try.success(JacksonUtil.convertFromJson(val, VersionedKey.class)))
                          .orElse(newKey(0L));

    }

    /**
     * @return true - if current data is stale and needs refreshed
     */
    @Override
    public boolean isOutOfDate() {

        return !versionedKey.equals(loadKeyFromS3().toJson());
    }

    /**
     * @return true - if current data is stale and needs refreshed
     */
    private boolean needsData() {
        return this.data.isLeft();
    }

    /**
     * Load data from remote store if stale
     */
    @Override
    public synchronized boolean load() {
        Either<Void, T> oldData = data;
        long oldModified = modified;
        String oldKey = versionedKey;
        try {
            if (isOutOfDate() || needsData()) {
                String newVersionedKey = reader.getAsString(key)
                                               .orElse(null);
                val loaded = nonAtomicload(newVersionedKey);
                data = Either.right((T) loaded._2());
                modified = loaded._1();
                versionedKey = newVersionedKey;
            } else {
                return false;
            }
        } catch (Throwable e) {
            data = oldData;
            versionedKey = oldKey;
            modified = oldModified;
            logger.info(e.getMessage(), e);
            throw ExceptionSoftener.throwSoftenedException(e);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private Tuple2<Long, Object> nonAtomicload(String newVersionedKey) throws Throwable {
        long lastMod = -1;
        while (modified >= lastMod) {
            lastMod = reader.getLastModified(newVersionedKey)
                            .getTime();
            if (modified < lastMod)
                Thread.sleep(backoff);
        }
        Data data = reader.<Data> getAsObject(newVersionedKey)
                .visit(t->t,e-> {
                              throw new ManifestComparatorKeyNotFoundException(
                                                                                "Missing versioned key "
                                                                                        + newVersionedKey
                                                                                        + " - likely data changed during read");
                          });
        logger.info("Loaded new data with date {} for key {}, versionedKey {}, versionedKey from data ",
                    new Object[] { data.getDate(), key, newVersionedKey, data.getVersionedKey() });
        return Tuple.tuple(lastMod, data.getData());
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
        VersionedKey currentVersionedKey = loadKeyFromS3();
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
        deleter.delete(withVersion);
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
    public synchronized void saveAndIncrement(T data) {
        Either<Void, T> oldData = this.data;
        final String oldKey = versionedKey;
        VersionedKey newVersionedKey = increment();
        final String newKey = newVersionedKey.toJson();
        logger.info("Saving data with key {}, new version is {}", key, newVersionedKey.toJson());

        try {
            writer.putSync(newVersionedKey.toJson(), new Data(
                                                              data, new Date(), newVersionedKey.toJson()))
                  .flatMap(res -> stringWriter.put(key, newVersionedKey.toJson()))
                  .peek(res -> {
                      this.data = Either.right(data);
                      delete(versionedKey);
                  }).peekFailed((err) -> {
                      String message = String.format("Failed to update manifest comparator file from %s to %s", oldKey, newKey);
                      logger.warn(message, err);
                  });

        } finally {
            versionedKey = newVersionedKey.toJson();
        }
    }

    @Override
    public String toString() {
        return "[S3ManifestComparator:key:" + key + ",versionedKey:" + JacksonUtil.serializeToJson(versionedKey) + "]";
    }

}
