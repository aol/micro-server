package com.aol.micro.server.utility;


import static java.util.Optional.ofNullable;

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
