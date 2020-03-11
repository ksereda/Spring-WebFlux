package com.example.demoexamples;

import java.util.Arrays;
import java.util.List;


import org.reactivestreams.Publisher;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * You can create your own flux:
 *         - using the generate() method
 *                 - using the create() method
 *                 - using just() method
 *                 - justOrEmpty - prints Mono<> if the element is not null, otherwise the completion signal
 *                 - fromArray()
 *                 - fromIterable()
 *                 - range()
 *                 - fromStream()
 *                 - fromSupplier()
 *                 - fromRunnable()
 *                 - fromFuture()
 *                 - from a third-party Publisher
 */

public class CreateFluxOrMono {
    public static void main(String[] args) {

        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);

        // This is empty Mono
        Mono<String> noData = Mono.empty();

        // The first parameter is the beginning of the range, and the second parameter is the number of elements to be produced
        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
        // начиная с 2010 - и так 9 значений (второй параметр - это кол-во значений)
        Flux<Disposable> result = (Flux<Disposable>) Flux.range(2010, 9)
                .subscribe(System.out::println);

        // Will output the values to the console. We get: 1, 2, 3
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe(System.out::println);

//        Publisher<String> publisher = redisson.getKeys().getKeys();
//        Flux<String> from = Flux.from(publisher);


         // создания пустых потоков данных
        Flux<String> empty = Flux.empty();
        Flux<String> never = Flux.never();  // создает поток, который не пошлет ни одного сигнала – onComplete, onNext или onError.
        Mono<String> error = Mono.error(new RuntimeException("Unknown id"));

    }
}
