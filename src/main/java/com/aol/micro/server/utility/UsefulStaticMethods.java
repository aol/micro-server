package com.aol.micro.server.utility;


import static java.util.Optional.ofNullable;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

public class UsefulStaticMethods {

	public static interface Loadable<T> {
		T load();
	}

	/**
	 * Returns a wrapper around a file with a load method that reads it to a String
	 * 
	 * usage
	 * 
	 * File data;
	 * 
	 * String text = asLoadable(data).load();	
	 *   
	 */
	public static Loadable<String> asLoadable(File f) {
		return () -> read(f);
	}

	public static String read(File f) {
		try {
			return FileUtils.readFileToString(f);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Returns a wrapper around an URL with a laodable method that reads the contents to a String
	 * 
	 * usage
	 * 
	 * String content = asLoadable("http://www.aol.com").load();
	 * 
	 */
	public static Loadable<String> asLoadableURL(String url) {
		return () -> getText(createURL(url));

	}

	public static URL createURL(String url) {

		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getText(URL url) {
		try {
			return DefaultGroovyMethods.getText(url);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isEmpty(Multimap m) {
		return Optional.ofNullable(m).orElse(TreeMultimap.create()).isEmpty();
	}

	public static <T> List<T> genericify(List list) {
		return (List<T>) list;
	}

	public static <T> T eitherOrNull(T option1, T option2) {
		if (option2 == null) {
			return ofNullable(option1).orElse(null);
		}
		return either(option1, option2);
	}

	public static <T> T either(T option1, T option2) {
		return ofNullable(option1).orElse(option2);
	}

	public static List eitherList(List option1, List option2) {
		if (!CollectionUtils.isEmpty(option1)) {
			return option1;
		} else {
			return option2;
		}

	}

	public static <T> T last(Collection<T> c) {
		if (CollectionUtils.isEmpty(c)) {
			return null;
		}
		return Iterables.getLast(c);

	}

	public static <T> Optional<T> headOptional(Collection<T> collection) {
		if (collection.size() == 0) {
			return Optional.empty();
		}
		return ofNullable(head(collection));
	}

	public static <T, V> List<V> transform(List<T> list, Function<? super T, ? extends V> function) {
		return Lists.transform(list, function);
	}

	public static <T> T head(Collection<T> collection) {
		return collection.iterator().next();
	}

	public static <T> List<T> tail(List<T> list) {

		return DefaultGroovyMethods.tail(list);
	}

	public static <T> ImmutableSortedSet<T> immutableSortedSet(T... elements) {
		TreeSet<T> result = new TreeSet<T>();
		for (T next : elements) {
			result.add(next);
		}
		return ImmutableSortedSet.copyOf(result);
	}

	public static <T> StringBuilder appendAll(StringBuilder builder, Collection<T> items, String sep) {
		if (items == null) {
			return builder;
		}
		for (T next : items) {
			builder.append(next).append(sep);
		}
		return builder;
	}
	
	public static <T> T unproxy(T a){
		if(AopUtils.isAopProxy(a) && a instanceof Advised) {
		    Object target;
			try {
				target = ((Advised)a).getTargetSource().getTarget();
			} catch (Exception e) {
				return a;
			}
		    return (T)target;
		}
		return a;

	}

}
