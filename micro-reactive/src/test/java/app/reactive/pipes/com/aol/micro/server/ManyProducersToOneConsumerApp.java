package app.reactive.pipes.com.aol.micro.server;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.reactive.Pipes;
import com.aol.simple.react.async.factories.QueueFactories;
import com.aol.simple.react.stream.traits.LazyFutureStream;

/**
 * Example using simple-react queue backed by an Agrona ManyToOneConcurrentArrayQueue
 * This is suitable for Many Producers and a Single consumer
 * 
 * @author johnmcclean
 *
 */
public class ManyProducersToOneConsumerApp {

	public static void main(String[] args){
		Pipes.register("test", QueueFactories.
											<String>boundedNonBlockingQueue(100)
												.build());
		LazyFutureStream<String> stream =  Pipes.futureStreamCPUBound("test");
		stream.filter(it->it!=null).peek(System.out::println).run();
		new MicroserverApp(()-> "simple-app").run();
	}
	
	
}
