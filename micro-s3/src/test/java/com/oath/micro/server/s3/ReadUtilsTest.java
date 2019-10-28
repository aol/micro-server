package com.oath.micro.server.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.oath.micro.server.s3.data.ReadUtils;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReadUtilsTest {

    @Test
    public void getInputStreamSupplier()
            throws AmazonClientException, InterruptedException, IOException {
        TransferManager transferManager = mock(TransferManager.class);
        Download download = mock(Download.class);

        when(transferManager.download(anyString(), anyString(), any())).thenReturn(download);

        File file = Files.createTempFile("micro-s3", "test")
                         .toFile();
        Assert.assertTrue(file.exists());
        ReadUtils utils = new ReadUtils(transferManager, "test");

        InputStream stream = utils.getInputStream("", "", () -> file);
        assertNotNull(stream);

        assertFalse(file.exists());
    }

    @Test
    public void getInputStreamDefaultSupplier()
            throws AmazonClientException, InterruptedException, IOException {
        TransferManager transferManager = mock(TransferManager.class);
        Download download = mock(Download.class);

        when(transferManager.download(anyString(), anyString(), any())).thenReturn(download);

        ReadUtils utils = new ReadUtils(transferManager, System.getProperty("java.io.tmpdir"));
        InputStream stream = utils.getInputStream("", "");
        assertNotNull(stream);
        verify(download).waitForCompletion();
    }


    @Test
    @SneakyThrows
    public void getFileInputStream() {
        TransferManager transferManager = mock(TransferManager.class);
        Download download = mock(Download.class);
        when(transferManager.download(anyString(), anyString(), any())).thenReturn(download);
        File localFile = File.createTempFile("micro-s3", "test");

        ReadUtils readUtils = new ReadUtils(transferManager,"test");

        FileInputStream fileInputStream = readUtils.getFileInputStream("bucket", "key", () -> localFile);
        assertNotNull(fileInputStream);

        verify(transferManager, times(1)).download(anyString(), anyString(), any(File.class));
        verify(download, times(1)).waitForCompletion();

    }
}
