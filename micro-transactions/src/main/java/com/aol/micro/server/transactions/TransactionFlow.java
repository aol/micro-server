package com.aol.micro.server.transactions;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.aol.cyclops.trycatch.Try;

/**
 * Stream-like monadic transaction processing
 * 
 * @author johnmcclean
 *
 * @param <T> Data input type for transactional process
 * @param <R> Data result type for transactional process
 */
@AllArgsConstructor
@Wither
public class TransactionFlow<T,R> {
	private final TransactionTemplate transactionTemplate;
	private final Function<T,R> transaction;
	
	
	/**
	 * Map from input -&gt; output within the context of a transaction.
	 * An exception in the mapping process will cause Rollback
	 * Transaction will be completed once the entire flow has completed
	 * <pre>
	 * {@code
	 * 	Integer result = TransactionFlow.<Integer>of(transactionTemplate)
										.map(this::load)
										.map(this::save)
										.execute(10)
										.get();
	 * }
	 * </pre>
	 * 
	 * @param fn Map from input type to output type within a transaction
	 * @return TransactionFlow 
	 */
	public <R1> TransactionFlow<T,R1> map(Function<R,R1> fn){
		return of(transactionTemplate,transaction.andThen(fn));
	}
	/**
	 * Consume current data within the context of a transaction.
	 * An exception in the consumption process will cause Rollback
	 * Transaction will be completed once the entire flow has completed
	 * <pre>
	 * {code
	 * 	Integer result = TransactionFlow.of(transactionTemplate, this::load)
										.consume(this::save)
										.execute(10)
										.get();
	 * }
	 * </pre>
	 * 
	 * @param c Consumer to accept current data
	 * @return TransactionFlow 
	 */
	public TransactionFlow<T,R> peek(Consumer<R> c){
		 return map (in-> { c.accept(in); return in;});
	}
	/**
	 * Transform data in the context of a Transaction and optionally propgate to / join with a new TransactionalFlow
	 *  * <pre>
	 * {code
	 * 	String result = TransactionFlow.of(transactionTemplate, this::load)
										.flatMap(this::newTransaction)
										.execute(10)
										.get();
	 * }
	 * </pre>
	 * 
	 * 
	 * @param fn flatMap function to be applied
	 * @return Next stage in the transactional flow
	 */
	public <R1> TransactionFlow<T,R1> flatMap(Function<R,TransactionFlow<T,R1>> fn){
		return of(transactionTemplate,a -> fn.apply(transaction.apply(a)).transaction.apply(a));
	}
	
	/**
	 * Execute the transactional flow - catch all exceptions
	 * 
	 * @param input Initial data input
	 * @return Try that represents either success (with result) or failure (with errors)
	 */
	public Try<R,Throwable> execute(T input){
		return Try.withCatch( ()->transactionTemplate.execute(status-> transaction.apply(input)));
		 
	}
	/**
	 * Execute the transactional flow - catch only specified exceptions
	 * 
	 * @param input Initial data input
	 * @param classes Exception types to catch
	 * @return Try that represents either success (with result) or failure (with errors)
	 */
	public <Ex extends Throwable> Try<R,Ex> execute(T input,Class<Ex> classes){
		
		return Try.withCatch( ()->transactionTemplate.execute(status-> transaction.apply(input)),classes);
		 
	}
	
	/**
	 * Create a new Transactional Flow
	 * 
	 * @param transactionManager Spring TransactionTemplate to manage the transactions
	 * @param fn Initial function to be applied 
	 * @return Create a new Transactional Flow
	 */
	public static <T,R> TransactionFlow<T,R> of(TransactionTemplate transactionManager,Function<T,R> fn){
		return new TransactionFlow<>(transactionManager,fn);
	}
	/**
	 *  Create a new Transactional Flow
	 * 
	 * @param transactionManager Spring TransactionTemplate to manage the transactions
	 * @return Create a new Transactional Flow
	 */
	public static <T> TransactionFlow<T,T> of(TransactionTemplate transactionManager){
		return new TransactionFlow<>(transactionManager,Function.identity());
	}
}
