package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 *
 */

public class OnBackPressureError_operator {
    public static void main(String[] args) {

        Flux.range(1, 10)
                .onBackpressureError()
                .map(index -> index + "_new")
                .subscribe(System.out::println);

    }
}
