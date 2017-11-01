package com.oath.micro.server.utility;

import static com.oath.micro.server.utility.UsefulStaticMethods.either;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class UsefulStaticMethodsTest {

	@Test
	public void testEither() {
		assertThat(either(null, "answer"), is("answer"));
		assertThat(either("answer", "different"), is("answer"));
	}

}
