package com.aol.micro.server.transactions;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.aol.cyclops.trycatch.Try;

@AllArgsConstructor
@Wither
public class TransactionFlow<T,R> {
	private final TransactionTemplate transactionTemplate;
	private final Function<T,R> transaction;
	
	
	public <R1> TransactionFlow<T,R1> map(Function<R,R1> fn){
		return of(transactionTemplate,transaction.andThen(fn));
	}
	public <R1> TransactionFlow<T,R1> flatMap(Function<R,TransactionFlow<T,R1>> fn){
		return of(transactionTemplate,a -> fn.apply(transaction.apply(a)).transaction.apply(a));
	}
	
	public Try<R,Exception> execute(T input){
		return Try.withCatch( ()->transactionTemplate.execute(status-> transaction.apply(input)));
		 
	}
	
	public static <T,R> TransactionFlow<T,R> of(TransactionTemplate transactionManager,Function<T,R> fn){
		return new TransactionFlow<>(transactionManager,fn);
	}
}
