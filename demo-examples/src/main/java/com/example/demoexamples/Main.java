package com.example.demoexamples;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {

        // Map
        // Map transforms the elements emitted by a Flux to other value by applying a synchronous function to each item.
        Flux.just(1, 5, 10)
                .map(num -> num * 10)
                .subscribe(System.out::println);


        // FlatMap
        // FlatMap transform the elements emitted by the Flux asynchronously into Publishers, then flatten these inner publishers into a single Flux through merging.
        Flux.just(1, 5, 10)
                .flatMap(num -> Flux.just(num * 10))
                .subscribe(System.out::println);


        // Concat
        // Below is 2 different flux that produces elements with a predefined delay. This process runs parallelly and hence we have a Thread.sleep() to view the final result of concat.
        // concatWith() returns a Flux whereas concat() returns Mono. Hence, with the commented line of code, you will see only 3 elements in the output.
        // mergeWith() merge data from a Flux and a Publisher into an interleaved merged sequence whereas concatWith() merge adds with no interleave.
        Flux<Integer> flux1 = Flux.just(1, 5, 10)
                .delayElements(Duration.ofMillis(200));

        Flux<Integer> flux2 = Flux.just(15, 20, 25)
                .delayElements(Duration.ofMillis(400));

        Mono<Integer> mono1 = Mono.just(1);


        flux1.concat(flux2).concat(mono1)
                .subscribe(System.out::println);

        flux1.concatWith(flux2).concatWith(mono1)
                .subscribe(System.out::println);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ZIP
        // It zips given Flux with another Publisher source, that is to say, wait for both to emit one element and combine these elements once into a Tuple
        Flux<Integer> flux3 = Flux.just(1, 5, 10)
                .delayElements(Duration.ofMillis(200));

        Flux<Integer> flux4 = Flux.just(15, 20, 25)
                .delayElements(Duration.ofMillis(400));

        Mono<Integer> mono2 = Mono.just(1);

        flux3.zipWith(flux4);

// Output:
// [1,15]
// [5,20]
// [10,25]


    }
}
