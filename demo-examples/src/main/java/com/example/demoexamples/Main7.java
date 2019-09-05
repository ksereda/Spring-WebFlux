package com.example.demoexamples;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Transform
 */

public class Main7 {
    public static void main(String[] args) {

        System.out.println("\nTransform:");
        // The transform operator allows you to encapsulate part of the operator into a function.
        // This function is applied to the original chain of operators during assembly to complement it with some encapsulated operators

        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);

        Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
//                .doOnNext(System.out::println)
                .transform(filterAndMap)
                .subscribe(d -> System.out.println("MapAndFilter for: " + d));



        System.out.println("\nExample 2::");
        // We can subscribe 3 times (3 different subscribers)

        Flux<String> source = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .map(String::toUpperCase);

        source.subscribe(d -> System.out.println("Subscriber 1: " + d));
        source.subscribe(d -> System.out.println("Subscriber 2: " + d));
        source.subscribe(d -> System.out.println("Subscriber 3: " + d));



        System.out.println("\nExample 3:");
        // The process runs regardless of when the subscriptions were attached.
        // Therefore, there may be a situation in which data will not be executed sequentially.

        DirectProcessor<String> hotSource = DirectProcessor.create();
        Flux<String> hotFlux = hotSource.map(String::toUpperCase);

        hotFlux.subscribe(d -> System.out.println("Subscriber 1: " + d));

        hotSource.onNext("blue");
        hotSource.onNext("green");

        hotFlux.subscribe(d -> System.out.println("Subscriber 2: " + d));

        hotSource.onNext("orange");
        hotSource.onNext("purple");

        hotSource.onComplete();






    }
}
