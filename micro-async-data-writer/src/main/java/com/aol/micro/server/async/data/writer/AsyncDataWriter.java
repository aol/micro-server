package com.aol.micro.server.async.data.writer;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import cyclops.async.Future;
import cyclops.collections.MapX;
import org.jooq.lambda.tuple.Tuple;


import com.aol.micro.server.events.SystemData;
import com.aol.micro.server.manifest.ManifestComparator;
import com.aol.micro.server.utility.HashMapBuilder;
import com.google.common.eventbus.EventBus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AsyncDataWriter<T> implements DataWriter<T> {
    public static final String MANIFEST_COMPARATOR_DATA_LOADER_KEY = "Manifest Comparator AsyncDataWriter Load Operation";
    public static final String MANIFEST_COMPARATOR_DATA_WRITER_KEY = "Manifest Comparator AsyncDataWriter Save and increment Operation";

    private final Executor executorService;
    private final ManifestComparator<T> comparator;
    private final Random r = new Random();
    private final EventBus bus;

    public static <T> AsyncDataWriter<T> asyncDataWriter(Executor executorService, ManifestComparator<T> comparator) {
        return asyncDataWriter(executorService, comparator, new EventBus());
    }

    public static <T> AsyncDataWriter<T> asyncDataWriter(Executor executorService, ManifestComparator<T> comparator,
            EventBus bus) {
        return new AsyncDataWriter<>(
                                     executorService, comparator, bus);
    }

    @Override
    public Future<T> loadAndGet() {
        String correlationId = "" + System.currentTimeMillis() + ":" + r.nextLong();
        Supplier<MapX<String, String>> dataMap = () -> MapX.fromMap(HashMapBuilder.map(MANIFEST_COMPARATOR_DATA_LOADER_KEY,
                                                                                       comparator.toString())
                                                                                  .build());

        return Future.ofSupplier(() -> Tuple.tuple(comparator.load(), comparator.getData()), executorService)
                      .peek(t -> bus.post(SystemData.<String, String> builder()
                                                    .correlationId(correlationId)
                                                    .dataMap(dataMap.get())
                                                    .errors(0)
                                                    .processed(t.v1 ? 1 : 0)
                                                    .build())) // add
                                                               // recover
                                                               // option
                                                               // here
                                                               // also
                                                               // with
                                                               // cyclops-react
                                                               // 1.0.0-final
                      .map(t -> t.v2);
    }

    @Override
    public Future<Void> saveAndIncrement(T data) {
        String correlationId = "" + System.currentTimeMillis() + ":" + r.nextLong();
        Supplier<MapX<String, String>> dataMap = () -> MapX.fromMap(HashMapBuilder.map(MANIFEST_COMPARATOR_DATA_WRITER_KEY,
                                                                                       comparator.toString())
                                                                                  .build());
        return Future.<Void> ofSupplier(() -> {
            comparator.saveAndIncrement(data);
            return null;
        } , executorService)
                      .peek(t -> bus.post(SystemData.<String, String> builder()
                                                    .correlationId(correlationId)
                                                    .dataMap(dataMap.get())
                                                    .errors(0)
                                                    .processed(1)
                                                    .build())); // add
                                                                // recover
                                                                // option
                                                                // here
                                                                // also
                                                                // with
                                                                // cyclops-react
                                                                // 1.0.0-final

    }

    @Override
    public Future<Boolean> isOutOfDate() {
        return Future.ofSupplier(() -> comparator.isOutOfDate(), executorService);
    }
}
