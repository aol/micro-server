package com.aol.micro.server.s3.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.function.Supplier;

import com.oath.cyclops.util.ExceptionSoftener;
import org.apache.commons.io.FileUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReadUtils {
    private final TransferManager transferManager;
    private final String tmpDirectory;

    /**
     * Method returns InputStream from S3Object. Multi-part download is used to
     * get file. s3.tmp.dir property used to store temporary files. You can
     * specify temporary file name by using tempFileSupplier object.
     * 
     * @param bucketName
     * @param key
     *            -
     * @param tempFileSupplier
     *            - Supplier providing temporary filenames
     * @return InputStream of
     * @throws AmazonServiceException
     * @throws AmazonClientException
     * @throws InterruptedException
     * @throws IOException
     */
    public InputStream getInputStream(String bucketName, String key, Supplier<File> tempFileSupplier)
            throws AmazonServiceException, AmazonClientException, InterruptedException, IOException {
        File file = tempFileSupplier.get();
        try {
            Download download = transferManager.download(bucketName, key, file);
            download.waitForCompletion();
            return new ByteArrayInputStream(
                                            FileUtils.readFileToByteArray(file));
        } finally {
            file.delete();
        }
    }

    /**
     * Method returns InputStream from S3Object. Multi-part download is used to
     * get file. s3.tmp.dir property used to store temporary files.
     * 
     * @param bucketName
     * @param key
     * @return
     * @throws AmazonServiceException
     * @throws AmazonClientException
     * @throws InterruptedException
     * @throws IOException
     */
    public InputStream getInputStream(String bucketName, String key)
            throws AmazonServiceException, AmazonClientException, InterruptedException, IOException {
        Supplier<File> tempFileSupplier = ExceptionSoftener.softenSupplier(() -> Files.createTempFile(FileSystems.getDefault()
                                                                                                                 .getPath(tmpDirectory),
                                                                                                      "micro-s3",
                                                                                                      "file")
                                                                                      .toFile());
        return getInputStream(bucketName, key, tempFileSupplier);
    }
}
