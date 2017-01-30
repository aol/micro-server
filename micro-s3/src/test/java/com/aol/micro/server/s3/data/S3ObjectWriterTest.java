package com.aol.micro.server.s3.data;

import static org.coursera.metrics.datadog.DatadogReporter.Expansion.COUNT;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.MEAN;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.MEDIAN;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.P95;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.P99;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.RATE_15_MINUTE;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.RATE_1_MINUTE;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
import com.aol.cyclops.control.FluentFunctions;
import com.aol.cyclops.control.Try;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;

import lombok.extern.slf4j.Slf4j;
import repeat.Repeat;
import repeat.RepeatRule;

@Slf4j
public class S3ObjectWriterTest {
    private static final String BUCKET = "aolp-lana-micro-s3";
    private static final Optional<byte[]> nullableFile;

    private static TransferManager manager;
    private static File tmpDir;
    private static Random r;
    private static MetricRegistry metricsRegistry = SharedMetricRegistries.getOrCreate("default");
    private Histogram unencryptedHist = getHistogram("com.aol.micro.server.s3.test.latency.unencrypted");
    private Histogram aes256Hist = getHistogram("com.aol.micro.server.s3.test.latency.aes256");

    static {
        final File file = new File(
                                   System.getProperty("test.file.full.path"));
        Try<byte[], Throwable> loadFileOperation = Try.of(1, Throwable.class)
                                                      .map(FluentFunctions.ofChecked(i -> {
                                                          return FileUtils.readFileToByteArray(file);
                                                      }));
        loadFileOperation.onFail(e -> log.error(e.getMessage()));
        nullableFile = Optional.ofNullable(loadFileOperation.get());

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
        manager = new TransferManager(
                                      credentials);
        tmpDir = new File(
                          System.getProperty("java.io.tmpdir"));
        r = new Random();

        assertTrue(nullableFile.isPresent());

    }

    private static Histogram getHistogram(String meterName) {
        return metricsRegistry.histogram(MetricRegistry.name(S3ObjectWriterTest.class, meterName));
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
    @Repeat(times = 1000, threads = 4)
    public void upload() {
        S3ObjectWriter writerWithoutEncryption = buildWriterWithEncryption(false);
        long startNE = System.currentTimeMillis();
        Try<UploadResult, Throwable> uploadWithoutEncryption = writerWithoutEncryption.putSync("uploadWithoutEncryption"
                + r.nextLong(), nullableFile.get());
        long endNE = System.currentTimeMillis();
        assertTrue(uploadWithoutEncryption.isSuccess());
        unencryptedHist.update(endNE - startNE);

        S3ObjectWriter writerWithEncryption = buildWriterWithEncryption(true);
        long startWE = System.currentTimeMillis();
        Try<UploadResult, Throwable> uploadWithEncryption = writerWithEncryption.putSync("uploadWithEncryption"
                + r.nextLong(), nullableFile.get());
        assertTrue(uploadWithEncryption.isSuccess());
        long endWE = System.currentTimeMillis();
        aes256Hist.update(endWE - startWE);
    }

    private S3ObjectWriter buildWriterWithEncryption(boolean aesEncryption) {
        return new S3ObjectWriter(
                                  manager, BUCKET, tmpDir, aesEncryption);
    }

}
