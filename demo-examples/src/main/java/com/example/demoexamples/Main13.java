package com.example.demoexamples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * zip
 * first
 * then
 * justOrEmpty
 * switchIfEmpty
 */

public class Main13 {

    static Flux<Person> personFlux(Flux<String> firstName, Flux<String> lastName, Flux<String> email) {
        return Flux.zip(firstName, lastName, email)
                .map(p -> new Person(p.getT1(), p.getT2(), p.getT3()));
    }

    static Mono<String> firstMono(Mono<String> mono1, Mono<String> mono2) {
        return Mono.first(mono1, mono2);
    }

    static Mono<Void> thenMethod(Flux<Integer> flux) {
        return flux.then();
    }

    static Mono<String> justOrEmptyMethod(String string) {
        return Mono.justOrEmpty(string);
    }

    static Mono<String> switchIfEmptyMethod(Mono<String> mono) {
        return mono.switchIfEmpty(Mono.just("default string"));
    }

    public static void main(String[] args) {

        System.out.println("Zip:");
        // zip()
        // Suppose there are three streams Flux<String> firstName, Flux<String> lastName, Flux<String> email. We need to make a Flux<Person> stream from this

        Flux<String> firstName = Flux.just("Petya", "Vasya", "Kirill");
        Flux<String> lastName = Flux.just("Ivanov", "Petrov", "Sereda");
        Flux<String> email = Flux.just("123@gmail.com", "sdf@gmail.com", "ks@gmail.com");

        personFlux(firstName, lastName, email);



        System.out.println("\nfirst");
        // first()
        // You need to return Mono, which emits the value faster

        Mono<String> mono1 = Mono.just("Test");
        Mono<String> mono2 = Mono.just("Test2");

        firstMono(mono1, mono2);



        System.out.println("\nthen");
        // then()
        // we subscribe to the second Publisher after the end of the first Publisher
        // (or for example: convert Flux to Mono, which emits a completion signal when the completion signal comes Flux)

        Flux<Integer> flux = Flux.range(1, 5);
        thenMethod(flux);



        System.out.println("\njustOrEmpty");
        // justOrEmpty()
        // prints Mono<> if the element is not null, otherwise the completion signal

        String string = new String("Peter");
        justOrEmptyMethod(string);



        System.out.println("\nswitchIfEmpty");
        // switchIfEmpty()
        // switch to another sequence if the current sequence is empty

        Mono<String> monoEmpty = Mono.empty();
        switchIfEmptyMethod(monoEmpty);

        // Testing this method
//        StepVerifier.create(switchIfEmptyMethod(monoEmpty))
//                .expectNext("default string")
//                .verifyComplete();

    }
}
