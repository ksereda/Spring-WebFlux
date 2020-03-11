package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 * GroupBy:
 * сгруппировать элементы реактивного потока по произ- вольному критерию
 */
public class GroupBy_operator {
    public static void main(String[] args) {

        // Example 1:
        System.out.println("Example 1:");
        Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .groupBy(i -> i % 2 == 0 ? "even:" : "odd:")
                .concatMap(Flux::collectList)
                .subscribe(System.out::println);



        // Example 2:
        System.out.println("\nExample 2:");
        Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .groupBy(i -> i % 2 == 0 ? "even:" : "odd:")
                .concatMap(g -> g.defaultIfEmpty(1)           // if empty groups, show them
                        .map(String::valueOf)                 // map to string
                        .startWith(g.key()))                  // start with the group's key
                .subscribe(System.out::println);



        // Example 3:
        System.out.println("\nExample 3:");
        Flux.just("Hello", "world")
                .map(String::toUpperCase)
                .flatMap(s -> Flux.fromArray(s.split("")))
                .groupBy(String::toString)
                .concatMap(Flux::collectList)
                .subscribe(System.out::println);


    }
}
