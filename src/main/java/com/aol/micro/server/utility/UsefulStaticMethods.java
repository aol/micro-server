package com.aol.micro.server.utility;


import static java.util.Optional.ofNullable;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
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

	
	public static <T> T either(T option1, T option2) {
		return ofNullable(option1).orElse(option2);
	}
	public static<T> T[] eitherArray(T[] option1, T[] option2) {
		if (option1!=null && option1.length>0) {
			return option1;
		} else {
			return option2;
		}

	}
	

}
