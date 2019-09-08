package com.example.demoexamples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Create Mono
 */

public class Main12 {

    static Flux<String> mergeFluxWithInterleave(Flux<String> flux1, Flux<String> flux2) {
        return Flux.merge(flux1, flux2);
    }

    static Flux<String> mergeFluxWithNoInterleave(Flux<String> flux1, Flux<String> flux2) {
        return Flux.concat(flux1, flux2);
    }

    public static void main(String[] args) {

        System.out.println("Example 1:");
        // Create empty Mono

        System.out.println(Mono.empty());



        System.out.println("\nExample 2:");
        // Mono emitting no signal

        System.out.println(Mono.never());



        System.out.println("\nExample 3:");
        // merge

        Flux<String> flux1 = Flux.just("1", "2", "3");
        Flux<String> flux2 = Flux.just("4", "5", "6");

        mergeFluxWithInterleave(flux1, flux2);



        System.out.println("\nExample 3:");
        // concat

        mergeFluxWithNoInterleave(flux1, flux2);


    }
}
