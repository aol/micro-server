package app.publisher.binder.direct;

import cyclops.reactive.collections.mutable.ListX;
import cyclops.reactive.ReactiveSeq;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LazyTest {

    @Test
    public void jdkSteam(){
        List<Integer> list = Stream.of(1,2,3)
                                    .peek(System.out::println)
                                    .collect(Collectors.toList());
    }
    @Test
    public void lazy(){
        ListX<Integer> list = ReactiveSeq.of(1,2,3)
                                         .peek(System.out::println)
                                         .toListX();


        list.size();
        System.out.println("List " + list);



    }
}
