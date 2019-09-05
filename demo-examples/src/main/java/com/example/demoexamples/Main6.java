package com.example.demoexamples;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

/**
 * generate()
 */

//        Output:
//
//        3 х 0 = 0
//        3 х 1 = 3
//        3 х 2 = 6
//        3 х 3 = 9
//        3 х 4 = 12
//        3 х 5 = 15
//        3 х 6 = 18
//        3 х 7 = 21
//        3 х 8 = 24
//        3 х 9 = 27
//        3 х 10 = 30

public class Main6 {
    public static void main(String[] args) {

        // Create a multiplication table for the number 3

        System.out.println("\nExample 1:");

        Flux<String> flux = Flux.generate(
                () -> 0,    // set the initial value
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3 * state);  // use the initial value for the row in the multiplication table = 3
                    if (state == 10) sink.complete();    // stop when we get to number 10
                    return state + 1;  // return the new state that we use in the next call
                });


        System.out.println("\nExample 2:");

        Flux<String> flux2 = Flux.generate(
                AtomicLong::new,   // we generate a mutable object
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3*i);
                    if (i == 10) sink.complete();
                    return state;
                });


        System.out.println("\nExample 3:");

        Flux<String> flux3 = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3*i);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> System.out.println("state: " + state));



    }

}
