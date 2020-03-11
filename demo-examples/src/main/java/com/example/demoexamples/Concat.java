package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 * оператор concat объединяет все источники, пересылая полученные элемен- ты дальше.
 * При объединении двух потоков этот оператор сначала извлекает и пересылает элементы из первого потока, а потом из второго;
 */

public class Concat {
    public static void main(String[] args) {

        // Example 1
        System.out.println("Example 1");
        Flux.concat(
                Flux.range(1, 3),
                Flux.range(4, 2),
                Flux.range(6, 5)
        ).subscribe(e ->    System.out.println("onNext: " + e));

        // Example 2
        System.out.println("\nExample 2");
        Flux<Integer> oddFlux = Flux.just(1, 3);
        Flux<Integer> evenFlux = Flux.just(2, 4);

        Flux.concat(evenFlux, oddFlux)
                .subscribe(value -> System.out.println("Outer: " + value));

    }
}
