package com.example.demoexamples;

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalTime;

public class Main11 {
    public static void main(String[] args) {

        System.out.println("Example 1:");
        // replace all letters with *

        Flux<String> flux = Flux.just("foo", "chain");
        flux = flux.map(secret -> secret.replaceAll(".", "*"));
        flux.subscribe(next -> System.out.println("Received: " + next));

//        Flux<String> secrets = Flux
//                .just("foo", "chain")
//                .map(secret -> secret.replaceAll(".", "*"))
//                .subscribe(next -> System.out.println("Received: " + next));



        System.out.println("\nExample 2:");
        // retryWhen

        Flux<String> flux2 =
                Flux.<String>error(new IllegalArgumentException())
                        .retryWhen(companion -> companion
                                .zipWith(Flux.range(1, 4),
                                        (error, index) -> {
                                            if (index < 4) return index;
                                            else throw Exceptions.propagate(error);
                                        })
                        );



        System.out.println("\nExample 3:");
        // here is how to implement an exponential backoff with retryWhen that delays retries and increase the delay between each attempt
        // (pseudocode: delay = attempt number * 100 milliseconds)

        Flux<String> flux3 =
                Flux.<String>error(new IllegalArgumentException())
                        .retryWhen(companion -> companion
                                .doOnNext(s -> System.out.println(s + " at " + LocalTime.now()))
                                .zipWith(Flux.range(1, 4), (error, index) -> {   // We use the retryWhen + zipWith trick to propagate the error after 3 retries.
                                    if (index < 4) return index;
                                    else throw Exceptions.propagate(error);
                                })
                                .flatMap(index -> Mono.delay(Duration.ofMillis(index * 100)))   // Through flatMap, we cause a delay that depends on the attempt’s index.
                                .doOnNext(s -> System.out.println("retried at " + LocalTime.now()))
                        );

//        Output:
//
//        first retry after about 100ms
//        second retry after about 200ms
//        third retry after about 300ms

    }
}
