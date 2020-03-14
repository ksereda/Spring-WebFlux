package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 * оператор onBackPressureLast действует подобно onBackPressureDrop, но сохраняет последний отброшенный элемент и передает его в нисходящий поток,
 * как только появляется возможность.
 * Таким способом всегда можно получить самый последний элемент – даже в ситуации переполнения
 */

public class OnBackPressureLatest_operator {
    public static void main(String[] args) {

        Flux.range(1, 10)
                .onBackpressureLatest()
                .map(index -> index + "_new")
                .subscribe(System.out::println);

    }
}
