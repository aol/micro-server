package com.oath.micro.server.manifest;

public interface ManifestComparator<T> {
    public <R> ManifestComparator<R> withKey(String key);

    public boolean isOutOfDate();

    public boolean load();

    public void cleanAll();

    public void clean(int numberToClean);

    public void saveAndIncrement(T data);

    /**
     * Gets data from ManifestComparator, blocks until it has been initialized
     * 
     * @return Initialized data from ManifestComparator
     */
    public T getData();

    /**
     * Gets data from ManifestComparator, returns null if unitialized
     * 
     * @return Current Data
     */
    public T getCurrentData();

}
