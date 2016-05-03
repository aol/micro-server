package com.aol.micro.server.reactive;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.NoSuchElementException;

import org.junit.Test;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.javaslang.Javaslang;
import com.aol.micro.server.javaslang.reactive.JavaslangReactive;
import com.aol.micro.server.javaslang.reactive.JavaslangValueSubscriber;

import javaslang.control.Either;
import javaslang.control.Option;
import javaslang.control.Try;
import javaslang.control.Try.NonFatalException;

public class JavaslangValueSubscriberTest {
	@Test
	public void maybeTest() {
		JavaslangValueSubscriber<Integer> sub = JavaslangValueSubscriber.subscriber();
		ReactiveSeq.of(1, 2, 3).subscribe(sub);

		Option<Integer> maybe = sub.option();
		assertThat(maybe.get(), equalTo(1));
	}

	@Test
	public void maybePublisherTest() {
		JavaslangValueSubscriber<Integer> sub = JavaslangValueSubscriber.subscriber();
		Javaslang.value(Option.of(1)).subscribe(sub);

		Option<Integer> maybe = sub.option();
		assertThat(maybe.get(), equalTo(1));
	}

	@Test
	public void maybePublisherTest2() {
		JavaslangValueSubscriber<Integer> sub = JavaslangValueSubscriber.subscriber();
		JavaslangReactive.subsribeToValue(Option.of(1), sub);

		Option<Integer> maybe = sub.option();
		assertThat(maybe.get(), equalTo(1));
	}

	@Test
	public void maybeNonePublisherTest() {
		JavaslangValueSubscriber<Integer> sub = JavaslangValueSubscriber.subscriber();
		JavaslangReactive.subsribeToValue(Option.none(), sub);

		Option<Integer> maybe = sub.option();
		assertFalse(maybe.isDefined());
	}

	@Test
	public void eitherPublisherTest() {
		JavaslangValueSubscriber<Integer> sub = JavaslangValueSubscriber.subscriber();
		Javaslang.value(Either.right(1)).subscribe(sub);

		Either<Throwable, Integer> maybe = sub.either();
		assertThat(maybe.get(), equalTo(1));
	}

	@Test
	public void eitherPublisherErrorTest() {
		JavaslangValueSubscriber<Integer> sub = JavaslangValueSubscriber.subscriber();
		JavaslangReactive.subsribeToValue(Either.left(1), sub);

		Either<Throwable, Integer> xor = sub.either();
		assertThat(xor.swap().get(), instanceOf(NoSuchElementException.class));
	}

	@Test
	public void eitherLeftPublisherErrorTest() {
		JavaslangValueSubscriber<Integer> sub = JavaslangValueSubscriber.subscriber();
		Javaslang.value(Either.<Integer, Integer> left(1)).subscribe(sub);

		Either<Integer, Throwable> xor = sub.either().swap();
		assertThat(xor.get(), instanceOf(NoSuchElementException.class));
	}

	@Test
	public void tryPublisherTest() {
		JavaslangValueSubscriber<Integer> sub = JavaslangValueSubscriber.subscriber();
		Javaslang.value(Try.success(1)).subscribe(sub);

		Try<Integer> maybe = sub.tryValue();
		assertThat(maybe.get(), equalTo(1));
	}

	@Test
	public void tryPublisherErrorTest() {
		JavaslangValueSubscriber<Integer> sub = JavaslangValueSubscriber.subscriber();
		JavaslangReactive.subsribeToValue(Either.left(1), sub);

		Try<Integer> xor = sub.tryValue();
		assertThat(xor.getCause(), instanceOf(NoSuchElementException.class));
	}

	@Test
	public void tryLeftPublisherErrorTest() {
		JavaslangValueSubscriber<Integer> sub = JavaslangValueSubscriber.subscriber();
		Javaslang.value(Either.<Integer, Integer> left(1)).subscribe(sub);

		Try<Integer> xor = sub.tryValue();
		assertThat(xor.getCause(), instanceOf(NoSuchElementException.class));
	}
}
