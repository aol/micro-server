package com.oath.micro.server.s3.data;

import static org.coursera.metrics.datadog.DatadogReporter.Expansion.COUNT;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.MEAN;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.MEDIAN;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.P95;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.P99;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.RATE_15_MINUTE;
import static org.coursera.metrics.datadog.DatadogReporter.Expansion.RATE_1_MINUTE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import cyclops.control.Try;
import org.coursera.metrics.datadog.DatadogReporter;
import org.coursera.metrics.datadog.transport.HttpTransport;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.model.UploadResult;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class S3DownloadSystemTest{
    private static final String BUCKET = System.getProperty("s3.bucket");

    private static TransferManager manager;
    private static File tmpDir;
    private static Random r;
    private static MetricRegistry metricsRegistry = SharedMetricRegistries.getOrCreate("default");
    private final Histogram downloadHist = getHistogram("com.aol.micro.server.s3.test.latency.download");

    static {
        manager = createManager();
        tmpDir = new File(
                          System.getProperty("java.io.tmpdir"));
        r = new Random();
        
    }

    private static Histogram getHistogram(String meterName) {
        return metricsRegistry.histogram(MetricRegistry.name(S3DownloadSystemTest.class, meterName));
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
        log.info("Reporting to datadog every {} {}", reportPeriod, reportUnit);
        reporter.start(reportPeriod, reportUnit);
    }
  
    @Test
    @Ignore
    public void download(){
        S3ObjectWriter writerWithEncryption = buildWriterWithEncryption(true);
        String name = "uploadWithEncryption" + r.nextLong();
        Try<UploadResult, Throwable> uploadWithEncryption = writerWithEncryption.putSync(name, "Microserver");
        assertTrue(uploadWithEncryption.isSuccess());
        
        ExecutorService executorService = mock(ExecutorService.class);
        S3Utils s3Utils = new S3Utils((AmazonS3Client) manager.getAmazonS3Client(), manager, tmpDir.getAbsolutePath(), true, executorService);
        S3Reader s3Reader = s3Utils.reader(BUCKET);
        long startD = System.currentTimeMillis();
        Try<String, Throwable> download = s3Reader.getAsObject(name);
        long endD = System.currentTimeMillis();
        assertTrue(download.isSuccess());
        assertEquals("Microserver",download.orElse(""));
        downloadHist.update(endD - startD);
    }

    private S3ObjectWriter buildWriterWithEncryption(boolean aesEncryption) {
        return new S3ObjectWriter(
                                  manager, BUCKET, tmpDir, aesEncryption);
    }

}
