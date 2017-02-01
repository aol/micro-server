package com.aol.micro.server.s3;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.aol.micro.server.s3.data.ReadUtils;

public class ReadUtilsTest {

    @Test
    public void getInputStreamSupplier()
            throws AmazonServiceException, AmazonClientException, InterruptedException, IOException {
        TransferManager transferManager = mock(TransferManager.class);
        Download download = mock(Download.class);

        when(transferManager.download(anyString(), anyString(), any())).thenReturn(download);

        File file = Files.createTempFile("micro-s3", "test")
                         .toFile();
        Assert.assertTrue(file.exists());
        ReadUtils utils = new ReadUtils(
                                        transferManager, "test");

        utils.getInputStream("", "", () -> file);

        Assert.assertFalse(file.exists());
    }

    @Test
    public void getInputStreamDefaultSupplier()
            throws AmazonServiceException, AmazonClientException, InterruptedException, IOException {
        TransferManager transferManager = mock(TransferManager.class);
        Download download = mock(Download.class);

        when(transferManager.download(anyString(), anyString(), any())).thenReturn(download);

        ReadUtils utils = new ReadUtils(
                                        transferManager, System.getProperty("java.io.tmpdir"));
        utils.getInputStream("", "");
        verify(download).waitForCompletion();
    }
}
