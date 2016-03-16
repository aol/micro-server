package com.aol.micro.server.reactive;

import static com.aol.cyclops.control.AnyM.fromIterable;
import static com.aol.micro.server.reactive.CyclopsReactor.from;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.reactivestreams.Publisher;

import com.aol.cyclops.control.AnyM;
import com.aol.cyclops.control.Do;
import com.aol.cyclops.types.MonadicValue;
import com.aol.cyclops.types.anyM.AnyMSeq;
import com.aol.cyclops.types.anyM.AnyMValue;
import com.aol.cyclops.util.function.QuadFunction;
import com.aol.cyclops.util.function.TriFunction;

public interface For {
	
	public interface Publishers {
		/**
		 * Perform a four level nested internal iteration over the provided Publishers
		 * 
		 * @param publisher2
		 *            Nested Stream to iterate over
		 
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,T3,R1,R2, R3, R> AnyMSeq< R> each4(Publisher<? extends T1> publisher,
											Function<? super T1, ? extends Publisher<R1>> publisher2, 
											BiFunction<? super T1, ? super R1,? extends Publisher<R2>> publisher3, 
											TriFunction<? super T1, ? super R1,? super R2,? extends Publisher<R3>> publisher4, 
										    QuadFunction<? super T1,? super R1, ? super R2,? super R3,? extends R> yieldingFunction){
			
			return Do.add(from(publisher))
							.withAnyM(publisher2.andThen( p->from(p) ) )
							.withAnyM(a->b->publisher3.andThen( p->from(p) ).apply(a,b) )
							.withAnyM(a->b->c->from(publisher4.apply(a,b,c)) )
							.yield(yieldingFunction);
					
		}
		/**
		 * Perform a four level nested internal iteration over the provided Publishers
		 * 
		 * @param publisher2
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,T3,R1,R2, R3, R> AnyMSeq< R> each4(Publisher<? extends T1> publisher,
											Function<? super T1, ? extends Publisher<R1>> publisher2, 
											BiFunction<? super T1, ? super R1,? extends Publisher<R2>> publisher3, 
											TriFunction<? super T1, ? super R1,? super R2,? extends Publisher<R3>> publisher4, 
											QuadFunction<? super T1,? super R1,? super R2,? super R3, Boolean> filterFunction,
										    QuadFunction<? super T1,? super R1, ? super R2,? super R3,? extends R> yieldingFunction){
			
			return Do.add(from(publisher))
							.withAnyM(publisher2.andThen( p->from(p) ) )
							.withAnyM(a->b->publisher3.andThen( p->from(p) ).apply(a,b) )
							.withAnyM(a->b->c->from(publisher4.apply(a,b,c)) )
							.filter(a->b->c->d->filterFunction.apply(a,b,c,d))
							.yield(yieldingFunction);
					
		}
	
		/**
		 * Perform a three level nested internal iteration over the provided Publishers
		 * 
		 * @param publisher1
		 *            Nested Stream to iterate over
		
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,R1,R2, R> AnyMSeq< R> each3(Publisher<? extends T1> publisher,
											Function<? super T1, ? extends Publisher<R1>> publisher1, 
											BiFunction<? super T1, ? super R1,? extends Publisher<R2>> publisher2, 
										    TriFunction<? super T1,? super R1, ? super R2,? extends R> yieldingFunction){
			
			return Do.add(from(publisher))
							.withAnyM(publisher1.andThen( p->from(p) ) )
							.withAnyM(a->b->publisher2.andThen( p->from(p) ).apply(a,b) )
							.yield(yieldingFunction);
					
		}
		/**
		 * Perform a three level nested internal iteration over the provided Publishers
		 * 
		 * @param publisher2
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,R1,R2, R> AnyMSeq< R> each3(Publisher<? extends T1> publisher,
											Function<? super T1, ? extends Publisher<R1>> publisher2, 
											BiFunction<? super T1, ? super R1,? extends Publisher<R2>> publisher3,
											TriFunction<? super T1,? super R1,? super R2, Boolean> filterFunction,
										    TriFunction<? super T1,? super R1, ? super R2,? extends R> yieldingFunction){
			
			return Do.add(from(publisher))
							.withAnyM(publisher2.andThen( p->from(p) ) )
							.withAnyM(a->b->publisher3.andThen( p->from(p) ).apply(a,b) )
							.filter(a->b->c->filterFunction.apply(a,b,c))
							.yield(yieldingFunction);
					
		}
	
		/**
		 * Perform a two level nested internal iteration over the provided Publishers
		 * 
		 * @param publisher2
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T,R1, R> AnyMSeq< R> each2(Publisher<? extends T> publisher,
											Function<? super T, ? extends Publisher<R1>> publisher2, 
										    BiFunction<? super T,? super R1, ? extends R> yieldingFunction){
			
			return Do.add(from(publisher))
							.withAnyM(publisher2.andThen( p->from(p) ) )
							.yield(yieldingFunction);
					
		}
	
		/**
		 * Perform a two level nested internal iteration over the provided Publishers
		 * 
		 * @param publisher2
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T,R1, R> AnyMSeq< R> each2(Publisher<? extends T> publisher,
											Function<? super T, ? extends Publisher<R1>> publisher2, 
											BiFunction<? super T,? super R1, Boolean> filterFunction,
										    BiFunction<? super T,? super R1, ? extends R> yieldingFunction){
			
			return Do.add(from(publisher))
							.withAnyM(publisher2.andThen( p->from(p) ) )
							.filter(a->b->filterFunction.apply(a,b))
							.yield(yieldingFunction);
					
		}
	}
	public interface Values {
		
		/**
		 * Perform a four level nested internal iteration over the provided MonadicValues
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		 
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,T3,R1,R2, R3, R> AnyMValue< R> each(MonadicValue<? extends T1> MonadicValue,
											Function<? super T1, ? extends MonadicValue<R1>> stream1, 
											BiFunction<? super T1, ? super R1,? extends MonadicValue<R2>> stream2, 
											TriFunction<? super T1, ? super R1,? super R2,? extends MonadicValue<R3>> stream3, 
										    QuadFunction<? super T1,? super R1, ? super R2,? super R3,? extends R> yieldingFunction){
			
			return AnyM.ofValue(Do.add(from(MonadicValue))
								  .withAnyM(stream1.andThen( p->from(p) ) )
								  .withAnyM(a->b->stream2.andThen( p->from(p) ).apply(a,b) )
								  .withAnyM(a->b->c->from(stream3.apply(a,b,c)) )
								  .yield(yieldingFunction).unwrap());
					
		}
		/**
		 * Perform a four level nested internal iteration over the provided MonadicValues
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,T3,R1,R2, R3, R> AnyMValue< R> each(MonadicValue<? extends T1> MonadicValue,
											Function<? super T1, ? extends MonadicValue<R1>> stream1, 
											BiFunction<? super T1, ? super R1,? extends MonadicValue<R2>> stream2, 
											TriFunction<? super T1, ? super R1,? super R2,? extends MonadicValue<R3>> stream3, 
											QuadFunction<? super T1,? super R1,? super R2,? super R3, Boolean> filterFunction,
										    QuadFunction<? super T1,? super R1, ? super R2,? super R3,? extends R> yieldingFunction){
			
			return AnyM.ofValue( Do.add(from(MonadicValue))
								   .withAnyM(stream1.andThen( p->from(p) ) )
							       .withAnyM(a->b->stream2.andThen( p->from(p) ).apply(a,b) )
							       .withAnyM(a->b->c->from(stream3.apply(a,b,c)) )
							       .filter(a->b->c->d->filterFunction.apply(a,b,c,d))
							       .yield(yieldingFunction).unwrap());
					
		}
	
		/**
		 * Perform a three level nested internal iteration over the provided MonadicValues
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,R1,R2, R> AnyMValue< R> each(MonadicValue<? extends T1> MonadicValue,
											Function<? super T1, ? extends MonadicValue<R1>> stream1, 
											BiFunction<? super T1, ? super R1,? extends MonadicValue<R2>> stream2, 
										    TriFunction<? super T1,? super R1, ? super R2,? extends R> yieldingFunction){
			
			return AnyM.ofValue( Do.add(from(MonadicValue))
									.withAnyM(stream1.andThen( p->from(p) ) )
									.withAnyM(a->b->stream2.andThen( p->from(p) ).apply(a,b) )
									.yield(yieldingFunction).unwrap());
					
		}
		/**
		 * Perform a three level nested internal iteration over the provided MonadicValues
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,R1,R2, R> AnyMValue< R> each(MonadicValue<? extends T1> MonadicValue,
											Function<? super T1, ? extends MonadicValue<R1>> stream1, 
											BiFunction<? super T1, ? super R1,? extends MonadicValue<R2>> stream2, 
											TriFunction<? super T1,? super R1,? super R2, Boolean> filterFunction,
										    TriFunction<? super T1,? super R1, ? super R2,? extends R> yieldingFunction){
			
			return AnyM.ofValue( Do.add(from(MonadicValue))
								   .withAnyM(stream1.andThen( p->from(p) ) )
								   .withAnyM(a->b->stream2.andThen( p->from(p) ).apply(a,b) )
								   .filter(a->b->c->filterFunction.apply(a,b,c))
								   .yield(yieldingFunction).unwrap());
					
		}
	
		/**
		 * Perform a two level nested internal iteration over the provided MonadicValues
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T,R1, R> AnyMValue< R> each(MonadicValue<? extends T> MonadicValue,
											Function<? super T, MonadicValue<R1>> stream1, 
										    BiFunction<? super T,? super R1, ? extends R> yieldingFunction){
			
			return AnyM.ofValue( Do.add(from(MonadicValue))
								   .withAnyM(stream1.andThen( p->from(p) ) )
								   .yield(yieldingFunction)
								   .unwrap());
					
		}
	
		/**
		 * Perform a two level nested internal iteration over the provided MonadicValues
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T,R1, R> AnyMValue< R> each(MonadicValue<? extends T> MonadicValue,
											Function<? super T, ? extends MonadicValue<R1>> stream1, 
											BiFunction<? super T,? super R1, Boolean> filterFunction,
										    BiFunction<? super T,? super R1, ? extends R> yieldingFunction){
			
			return AnyM.ofValue( Do.add(from(MonadicValue))
								   .withAnyM(stream1.andThen( p->from(p) ) )
								   .filter(a->b->filterFunction.apply(a,b))
								   .yield(yieldingFunction).unwrap());
					
		}
		
		
	}
	public interface Iterables {
		/**
		 * Perform a four level nested internal iteration over the provided Iterables
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		 
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,T3,R1,R2, R3, R> AnyMSeq< R> each(Iterable<? extends T1> Iterable,
											Function<? super T1, ? extends Iterable<R1>> stream1, 
											BiFunction<? super T1, ? super R1,? extends Iterable<R2>> stream2, 
											TriFunction<? super T1, ? super R1,? super R2,? extends Iterable<R3>> stream3, 
										    QuadFunction<? super T1,? super R1, ? super R2,? super R3,? extends R> yieldingFunction){
			
			return Do.add(fromIterable(Iterable))
							.withAnyM(stream1.andThen( p->fromIterable(p) ) )
							.withAnyM(a->b->stream2.andThen( p->fromIterable(p) ).apply(a,b) )
							.withAnyM(a->b->c->fromIterable(stream3.apply(a,b,c)) )
							.yield(yieldingFunction);
					
		}
		/**
		 * Perform a four level nested internal iteration over the provided Iterables
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,T3,R1,R2, R3, R> AnyMSeq< R> each(Iterable<? extends T1> Iterable,
											Function<? super T1, ? extends Iterable<R1>> stream1, 
											BiFunction<? super T1, ? super R1,? extends Iterable<R2>> stream2, 
											TriFunction<? super T1, ? super R1,? super R2,? extends Iterable<R3>> stream3, 
											QuadFunction<? super T1,? super R1,? super R2,? super R3, Boolean> filterFunction,
										    QuadFunction<? super T1,? super R1, ? super R2,? super R3,? extends R> yieldingFunction){
			
			return Do.add(fromIterable(Iterable))
							.withAnyM(stream1.andThen( p->fromIterable(p) ) )
							.withAnyM(a->b->stream2.andThen( p->fromIterable(p) ).apply(a,b) )
							.withAnyM(a->b->c->fromIterable(stream3.apply(a,b,c)) )
							.filter(a->b->c->d->filterFunction.apply(a,b,c,d))
							.yield(yieldingFunction);
					
		}
	
		/**
		 * Perform a three level nested internal iteration over the provided Iterables
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,R1,R2, R> AnyMSeq< R> each(Iterable<? extends T1> Iterable,
											Function<? super T1, ? extends Iterable<R1>> stream1, 
											BiFunction<? super T1, ? super R1,? extends Iterable<R2>> stream2, 
										    TriFunction<? super T1,? super R1, ? super R2,? extends R> yieldingFunction){
			
			return Do.add(Iterable)
							.withAnyM(stream1.andThen( p->fromIterable(p) ) )
							.withAnyM(a->b->stream2.andThen( p->fromIterable(p) ).apply(a,b) )
							.yield(yieldingFunction);
					
		}
		/**
		 * Perform a three level nested internal iteration over the provided Iterables
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T1,T2,R1,R2, R> AnyMSeq< R> each(Iterable<? extends T1> Iterable,
											Function<? super T1, ? extends Iterable<R1>> stream1, 
											BiFunction<? super T1, ? super R1,? extends Iterable<R2>> stream2, 
											TriFunction<? super T1,? super R1,? super R2, Boolean> filterFunction,
										    TriFunction<? super T1,? super R1, ? super R2,? extends R> yieldingFunction){
			
			return Do.add(fromIterable(Iterable))
							.withAnyM(stream1.andThen( p->fromIterable(p) ) )
							.withAnyM(a->b->stream2.andThen( p->fromIterable(p) ).apply(a,b) )
							.filter(a->b->c->filterFunction.apply(a,b,c))
							.yield(yieldingFunction);
					
		}
	
		/**
		 * Perform a two level nested internal iteration over the provided Iterables
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T,R1, R> AnyMSeq< R> each(Iterable<? extends T> iterable,
											Function<? super T,? extends Iterable<R1>> stream1, 
										    BiFunction<? super T,? super R1, ? extends R> yieldingFunction){
			
			return Do.add(fromIterable(iterable))
							.withIterable(stream1.andThen( p->fromIterable(p) ) )
							.yield(yieldingFunction);
					
		}
	
		/**
		 * Perform a two level nested internal iteration over the provided Iterables
		 * 
		 * @param stream1
		 *            Nested Stream to iterate over
		 * @param filterFunction
		 *            Filter to apply over elements before passing non-filtered
		 *            values to the yielding function
		 * @param yieldingFunction
		 *            Function with pointers to the current element from both
		 *            Streams that generates the new elements
		 * @return SequenceM with elements generated via nested iteration
		 */
		static <T,R1, R> AnyMSeq< R> each(Iterable<? extends T> iterable,
											Function<? super T, ? extends Iterable<R1>> stream1, 
											BiFunction<? super T,? super R1, Boolean> filterFunction,
										    BiFunction<? super T,? super R1, ? extends R> yieldingFunction){
			
			return Do.add(fromIterable(iterable))
							.withAnyM(stream1.andThen( p->fromIterable(p) ) )
							.filter(a->b->filterFunction.apply(a,b))
							.yield(yieldingFunction);
					
		}
	}

}
