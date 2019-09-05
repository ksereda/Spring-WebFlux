package com.example.demoexamples;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * - grouping
 * - windowing
 * - buffering
 */

public class Main9 {
    public static void main(String[] args) {

        System.out.println("Grouping");

        StepVerifier.create(
                Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                        .groupBy(i -> i % 2 == 0 ? "even" : "odd")

                        .concatMap(g -> g.defaultIfEmpty(-1)   // if empty groups, show them
                                .map(String::valueOf)          // map to string
                                .startWith(g.key()))           // start with the group's key
        );
//                .expectNext("odd", "1", "3", "5", "11", "13")
//                .expectNext("even", "2", "4", "6", "12")
//                .verifyComplete();



        System.out.println("\nWindowing");

        StepVerifier.create(
                Flux.range(1, 10)
                        .window(5, 3)             // overlapping windows
                        .concatMap(g -> g.defaultIfEmpty(-1))   // show empty windows as -1
        );
//                .expectNext(1, 2, 3, 4, 5)
//                .expectNext(4, 5, 6, 7, 8)
//                .expectNext(7, 8, 9, 10)
//                .expectNext(10)
//                .verifyComplete();



        System.out.println("\nBuffering");

        StepVerifier.create(
                Flux.range(1, 10)
                        .buffer(5, 3)     // overlapping buffers
        );
//                .expectNext(Arrays.asList(1, 2, 3, 4, 5))
//                .expectNext(Arrays.asList(4, 5, 6, 7, 8))
//                .expectNext(Arrays.asList(7, 8, 9, 10))
//                .expectNext(Collections.singletonList(10))
//                .verifyComplete();


    }
}
