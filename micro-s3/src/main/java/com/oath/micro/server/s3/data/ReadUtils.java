package com.oath.micro.server.s3.data;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.function.Supplier;

import static java.nio.file.FileSystems.getDefault;

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
            return new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
        } finally {
            file.delete();
        }
    }

    /**
     * Return the InputStream for an S3Object. This API download (via multi-part) S3 object to a local file and return a
     * InputStream to that file. The local file will be deleted when the close is called on the stream.
     *
     * @param bucketName S3 bucket name
     * @param key key for the S3Object
     *
     * @return input stream to the downloaded file
     *
     * @throws AmazonClientException
     * @throws InterruptedException
     * @throws IOException
     */
    public InputStream getFileInputStream(String bucketName, String key)
        throws AmazonClientException, InterruptedException, IOException {
        File file = createTmpFile();

        Download download = transferManager.download(bucketName, key, file);
        download.waitForCompletion();
        return Files.newInputStream(file.toPath(), StandardOpenOption.DELETE_ON_CLOSE);
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
        return getInputStream(bucketName, key, this::createTmpFile);
    }

    @SneakyThrows
    private File createTmpFile() {
        return Files.createTempFile(getDefault().getPath(tmpDirectory), "micro-s3", "file").toFile();
    }
}
