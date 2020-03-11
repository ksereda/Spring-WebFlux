package com.example.demoexamples;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import reactor.core.publisher.Flux;

/**
 * Оператор flatMap логически состоит из двух операций: map (отображение) и flatten (преобразование в плоское представление,
 * в терминах Reactor эта операция напоминает оператор merge). Операция отображения (map) в операторе flatMap преобразует каждый входящий элемент в реактивный поток (T -> Flux<R>),
 * а операция преобразования в плоское представление сливает сгенерированные реактивные последовательности в новую реактивную последовательность,
 * передавая через нее элементы типа R.
 */

public class FlatMap_operator {
    public static void main(String[] args) {

        // Example 1
        System.out.println("Example 1:");
        Flux.just("1,2,3", "4,5,6")
                .flatMap(i -> Flux.fromIterable(Arrays.asList(i.split(","))))
                .collect(Collectors.toList())
                .subscribe(System.out::println);



        // Example 2:
        System.out.println("\nExample 2:");
        Flux.range(1, 10)
                .flatMap(v -> {
                    if (v < 5) {
                        return Flux.just(v * v);
                    }
                    return Flux.<Integer>error(new IOException("Error: "));
                })
                .subscribe(System.out::println, Throwable::printStackTrace);



        // Example 3:
        System.out.println("\nExample 3:");
        List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f");

        Flux.fromIterable(list)
                .flatMap( s -> {
                    return Flux.just(s + "x");
                })
                .collect(Collectors.toList())
                .subscribe(System.out::println);



        // Example 4:
        System.out.println("\nExample 4:");
        Flux.just("Hello", "world")
                .flatMap(s -> Flux.fromArray(s.split("")))
                .subscribe(System.out::println);

    }
}
