package com.oath.micro.server.s3.data;

import static org.coursera.metrics.datadog.DatadogReporter.Expansion.*;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cyclops.control.Try;
import cyclops.function.FluentFunctions;
import org.apache.commons.io.FileUtils;
import org.coursera.metrics.datadog.DatadogReporter;
import org.coursera.metrics.datadog.transport.HttpTransport;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.model.UploadResult;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;

import lombok.extern.slf4j.Slf4j;
import repeat.Repeat;
import repeat.RepeatRule;

@Slf4j
public class S3UploadSystemTest{
    private static final String BUCKET = System.getProperty("s3.bucket");
    private static final Optional<byte[]> nullableFile;

    private static TransferManager manager;
    private static File tmpDir;
    private static Random r;
    private static MetricRegistry metricsRegistry = SharedMetricRegistries.getOrCreate("default");
    private final Histogram unencryptedHist = getHistogram("com.aol.micro.server.s3.test.latency.unencrypted");
    private final Histogram aes256Hist = getHistogram("com.aol.micro.server.s3.test.latency.aes256");

    static {
        nullableFile = createNullableFile();

        assertTrue(nullableFile.isPresent());
        
        manager = createManager();
        tmpDir = new File(
                          System.getProperty("java.io.tmpdir"));
        r = new Random();
        
    }

    private static Histogram getHistogram(String meterName) {
        return metricsRegistry.histogram(MetricRegistry.name(S3UploadSystemTest.class, meterName));
    }

    private static Optional<byte[]> createNullableFile() {
        final File file = new File(
                                   System.getProperty("test.file.full.path"));
        Try<byte[], Throwable> loadFileOperation = Try.success(1, Throwable.class)
                                                      .map(FluentFunctions.ofChecked(i -> {
                                                          return FileUtils.readFileToByteArray(file);
                                                      }));
        loadFileOperation.onFail(e -> log.error(e.getMessage()));
        Optional<byte[]> nFile = loadFileOperation.toOptional();

        return nFile;
    }

    private static TransferManager createManager() {
        AWSCredentials credentials = new AWSCredentials() {

            @Override
            public String getAWSAccessKeyId() {
                return System.getProperty("s3.accessKey");
            }

            @Override
            public String getAWSSecretKey() {
                return System.getProperty("s3.secretKey");
            }

        };
        return new TransferManager(
                                      credentials);
    }

    @BeforeClass
    public static void setupMetrics() {
        EnumSet<DatadogReporter.Expansion> expansions = EnumSet.of(COUNT, RATE_1_MINUTE, RATE_15_MINUTE, MEDIAN, MEAN,
                                                                   P95, P99);
        HttpTransport httpTransport = new HttpTransport.Builder().withApiKey(System.getProperty("datadog.key"))
                                                                 .build();
        DatadogReporter.Builder builder = DatadogReporter.forRegistry(metricsRegistry)
                                                         .withTransport(httpTransport)
                                                         .withExpansions(expansions)
                                                         .withTags(Arrays.asList("stage:dev"));
        DatadogReporter reporter = builder.build();
        int reportPeriod = 5;
        TimeUnit reportUnit = TimeUnit.valueOf("SECONDS");
        log.info("Reporting to datadog every {} {}", 5, reportUnit);
        reporter.start(reportPeriod, reportUnit);
    }

    @Rule
    public RepeatRule rule = new RepeatRule();

    @Test
    @Ignore
    @Repeat(times = 1, threads = 4)
    public void upload() {
        S3ObjectWriter writerWithoutEncryption = buildWriterWithEncryption(false);
        long startNE = System.currentTimeMillis();
        Try<UploadResult, Throwable> uploadWithoutEncryption = writerWithoutEncryption.putSync("uploadWithoutEncryption" + r.nextLong(), nullableFile.get());
        long endNE = System.currentTimeMillis();
        assertTrue(uploadWithoutEncryption.isSuccess());
        unencryptedHist.update(endNE - startNE);

        S3ObjectWriter writerWithEncryption = buildWriterWithEncryption(true);
        long startWE = System.currentTimeMillis();
        Try<UploadResult, Throwable> uploadWithEncryption = writerWithEncryption.putSync("uploadWithEncryption" + r.nextLong(), nullableFile.get());
        assertTrue(uploadWithEncryption.isSuccess());
        long endWE = System.currentTimeMillis();
        aes256Hist.update(endWE - startWE);
        
        
    }

    private S3ObjectWriter buildWriterWithEncryption(boolean aesEncryption) {
        return new S3ObjectWriter(
                                  manager, BUCKET, tmpDir, aesEncryption);
    }

}
