package app.reactive.pipes.com.aol.micro.server;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.reactive.Pipes;
import com.aol.simple.react.async.QueueFactories;
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
		LazyFutureStream<String> stream = Pipes.registerForIO("test", QueueFactories.
											<String>boundedNonBlockingQueue(100)
												.build());
		stream.filter(it->it!=null).async().peek(System.out::println).sync().run();
		new MicroserverApp(()-> "simple-app").run();
	}
	
	
}
