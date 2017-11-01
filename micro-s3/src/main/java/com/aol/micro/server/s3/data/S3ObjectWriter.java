package com.aol.micro.server.s3.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import cyclops.control.Eval;
import cyclops.control.Try;
import cyclops.function.FluentFunctions;
import org.apache.commons.io.FileUtils;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3ObjectWriter {

    private final TransferManager manager;
    private final String bucket;
    private final File dir;
    private final boolean aes256Encryption;
    private final Random r = new Random();

    /**
     * 
     * Writes java Objects to defined S3 bucket with provided key. Calling
     * map / flatMap on the returned try instance will catch any exceptions, any
     * exceptions thrown will convert a Success to a Failure
     * 
     * This call is non-blocking.
     * 
     * @param key
     *            To read
     * @return Data as String
     */
    public Try<Upload, Throwable> put(String key, Object value) {

        return createObjectRequest(key, value).map(por -> {
            Upload upload = manager.upload(por);
            return upload;
        });

    }

    private Try<PutObjectRequest, Throwable> createObjectRequest(String key, Object value) {

        return writeToTmpFile(value).map(FluentFunctions.ofChecked(f -> {
            byte[] ba = FileUtils.readFileToByteArray(f);
            InputStream is = new ByteArrayInputStream(
                                                      ba);

            ObjectMetadata md = createMetadata(ba.length);

            PutObjectRequest pr = new PutObjectRequest(
                                                       bucket, key, is, md);
            return pr;
        }));
    }

    private Try<File, Throwable> writeToTmpFile(Object value) {
        String fileName = "" + System.currentTimeMillis() + "_" + r.nextLong();
        File file = new File(
                             dir, fileName);

        return Try.success(1, Throwable.class)
                  .map(FluentFunctions.ofChecked(i -> {
                      FileOutputStream fs = new FileOutputStream(
                                                                 file);

                      ObjectOutputStream oos = new ObjectOutputStream(
                                                                      fs);
                      oos.writeObject(value);
                      oos.flush();
                      oos.close();
                      return file;
                  }));
    }

    /**
     * Metadata object creation
     * @param length 
     * 
     * @return Metadata with AES_256 encryption if enabled
     */
    private ObjectMetadata createMetadata(int length) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(length);
        if (aes256Encryption)
            metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        return metadata;
    }

    /**
     * Blocking call
     * 
     * @param key
     *            with which to store data
     * @param value
     *            Data value
     * @return Try with completed result of operation
     */
    public Try<UploadResult, Throwable> putSync(String key, Object value) {
        return put(key, value).map(FluentFunctions.ofChecked(i -> i.waitForUploadResult()));
    }

    /**
     * Non-blocking call that will throw any Exceptions in the traditional
     * manner on access
     * 
     * @param key
     * @param value
     * @return
     */
    public Eval<UploadResult> putAsync(String key, Object value) {
        return Eval.later(() -> put(key, value))
                   .map(t -> t.orElse(null))
                   .map(FluentFunctions.ofChecked(up -> up.waitForUploadResult()));

    }

}
