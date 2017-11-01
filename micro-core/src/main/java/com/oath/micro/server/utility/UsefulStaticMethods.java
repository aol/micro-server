package com.oath.micro.server.utility;


import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	public static <T> List<T> concat(Collection<T> a,
			Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

}
