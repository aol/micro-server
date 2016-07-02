package com.aol.micro.server.s3.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.aol.cyclops.control.Eval;
import com.aol.cyclops.control.FluentFunctions;
import com.aol.cyclops.control.FutureW;
import com.aol.cyclops.control.Try;

import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public class S3ObjectWriter {

	private final TransferManager manager;
	private final String bucket;
	private final File dir;
	private final Random r = new Random();
	/**
	 * 
	 * Read data from defined S3 bucket with provided key to a String.
	 * Calling map / flatMap on the returned try instance will catch any exceptions, any exceptions thrown will convert a Success to a Failure
	 * 
	 * This call is non-blocking.
	 * 
	 * @param key To read
	 * @return Data as String
	 */
	public Try<Upload, Throwable> put(String key, Object value){
		
		 return Try.of(1, Throwable.class).map( FluentFunctions.ofChecked(i -> {
			String file = "" + System.currentTimeMillis() + "_" +r.nextLong();
			FileOutputStream fs = new FileOutputStream(new File(dir,file));
		
		
			ObjectOutputStream oos = new ObjectOutputStream(fs);
			oos.writeObject(value);
			oos.flush();
			oos.close();
		
			Upload upload = manager.upload(bucket, key, new File(dir,file));
		
			return upload;
		 }));
		
	
	}
	/**
	 * Blocking call
	 * 
	 * @param key with which to store data
	 * @param value Data value
	 * @return Try with completed result of operation
	 */
	public Try<UploadResult, Throwable> putSync(String key, Object value){
		return put(key,value).map(FluentFunctions.ofChecked(i->i.waitForUploadResult()));
	}
	/**
	 * Non-blocking call that will throw any Exceptions in the traditional manner on access
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Eval<UploadResult> putAsync(String key, Object value){
		return Eval.later(()->put(key,value)).map(t->t.get()).map(FluentFunctions.ofChecked(up->up.waitForUploadResult()));
	
	}
	
}
