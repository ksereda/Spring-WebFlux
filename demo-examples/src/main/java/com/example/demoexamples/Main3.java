package com.example.demoexamples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Main3 {
    public static void main(String[] args) {

        // 1) then/thenMany

        System.out.println("Example 1: ");
        // We can also combine Publisher’s for a common effect.
        // Subscribe to the second publisher after the end of the first publisher
        Flux<Integer> oddFlux = Flux.just(1, 3, 5);
        Flux<Integer> evenFlux = Flux.just(2, 4, 6);
        oddFlux.thenMany(evenFlux)
                .subscribe(value -> System.out.println("Value: " + value));


        System.out.println("\nExample 2:");
        // Get both Publishers
        Consumer<Integer> loggingConsumer = (value) -> System.out.println("Value: " + value);

        Flux<Integer> oddFlux2 = Flux.just(1, 3, 5);
        Flux<Integer> evenFlux2 = Flux.just(2, 4, 6);
        oddFlux2.doOnNext(loggingConsumer)
                .thenMany(evenFlux2)
                .subscribe(loggingConsumer);


        // 2) mergeWith

        System.out.println("\nExample 3:");
        // Combine the elements of two Publisher’s into one
        Flux<Integer> oddFlux3 = Flux.just(1, 3);
        Flux<Integer> evenFlux3 = Flux.just(2, 4)
                .doOnNext(value -> System.out.println("Inner: " + value));
        oddFlux3.mergeWith(evenFlux3)
                .subscribe(value -> System.out.println("Outer: " + value));


        System.out.println("\nExample 4:");
        // Sort when merged
        Flux<Integer> oddFlux4 = Flux.just(1, 3);
        Flux<Integer> evenFlux4 = Flux.just(2, 4);
        oddFlux4.mergeOrderedWith(evenFlux4, Comparator.naturalOrder())
                .subscribe(value -> System.out.println("Value: " + value));



        // zipWith

        System.out.println("\nExample 5:");
        // combine data from two Publisher’s
        Flux.just("a", "b", "c")
                .zipWith(Flux.just(1, 2, 3), (word, number) -> word + number)
                .subscribe(System.out::println);


    }
}
