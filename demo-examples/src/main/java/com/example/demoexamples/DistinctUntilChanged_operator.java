package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 * Используется для устранения дубликатов, следующих друг за другом
 */

public class DistinctUntilChanged_operator {
    public static void main(String[] args) {

        Flux.just(1, 1, 2, 2, 3, 4, 4, 4, 5, 6, 2, 2, 3, 3, 3, 1, 1, 1)
                .distinctUntilChanged()
                .subscribe(System.out::println);

    }
}
