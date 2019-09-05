package com.example.demoexamples;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * ParallelFlux
 */

public class Main10 {
    public static void main(String[] args) {

        System.out.println("\nExample 1:");
        Flux.range(1, 10)
                .parallel(2)   // we explicitly specified 2 processes, instead of letting the program itself determine how many they need, depending on the processor cores
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));


        System.out.println("\nExample 2:");
        Flux.range(1, 10)
                .parallel(2)
                .runOn(Schedulers.parallel())   // here is the execution in two threads
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));

    }
}
