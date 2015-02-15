package com.aol.micro.server.utility;

import java.util.HashMap;
import java.util.Map;

public class HashMapBuilder {

	public static <K, V> Builder<K, V> of(K key, V value) {
		return new Builder<K, V>(key, value);
	}
	public static <K, V> Builder<K, V> of(K key, V value,K key1, V value1) {
		return new Builder<K, V>(key, value).put(key1, value1);
	}
	public static <K, V> Builder<K, V> of(K key, V value,K key1, V value1,K key2, V value2) {
		return new Builder<K, V>(key, value).put(key, value, key1, value1, key2, value2);
	}
	public static <K, V> Builder<K, V> of(K key, V value,K key1, V value1,K key2, V value2,K key3, V value3) {
		return new Builder<K, V>(key, value).put(key, value, key1, value1, key2, value2,key3, value3);
	}

	public static final class Builder<K, V> {
		private final Map<K, V> build;

		public Builder(K key, V value) {
			build = new HashMap<K, V>();
			build.put(key, value);
		}

		public Builder<K, V> put(K key, V value) {
			build.put(key, value);
			return this;
		}
		public Builder<K, V> put(K key, V value,K key1, V value1) {
			build.put(key, value);
			build.put(key1, value1);
			return this;
		}
		public Builder<K, V> put(K key, V value,K key1, V value1,K key2, V value2) {
			build.put(key, value);
			build.put(key1, value1);
			build.put(key2, value2);
			return this;
		}
		public Builder<K, V> put(K key, V value,K key1, V value1,K key2, V value2,K key3, V value3) {
			build.put(key, value);
			build.put(key1, value1);
			build.put(key2, value2);
			build.put(key3, value3);
			return this;
		}

		public Map<K, V> build() {
			return build;
		}
	}

}
