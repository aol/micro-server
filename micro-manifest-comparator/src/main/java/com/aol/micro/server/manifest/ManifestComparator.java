package com.aol.micro.server.manifest;

public interface ManifestComparator<T> {
	public <R> ManifestComparator<R> withKey(String key);

	public boolean isOutOfDate();

	public boolean load();

	public void cleanAll();

	public void clean(int numberToClean);

	public void saveAndIncrement(T data);

	public T getData();

}
