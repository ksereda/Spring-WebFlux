package com.example.demoexamples;

import java.util.Arrays;
import java.util.function.Function;


import reactor.core.publisher.Flux;

/**
 * Оператор transform позволяет вам инкапсулировать часть оператора в функцию.
 * Эта функция применяется к исходной цепочке операторов во время сборки, чтобы дополнить ее некоторыми инкапсулированными операторами.
 * Т.е. мы берем отдельный метод фильтра и map в отдельную функцию, а затем инкапсулируем ее в нашу логику.
 */

public class Transform_operator {
    public static void main(String[] args) {

        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);

        Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .transform(filterAndMap)
                .subscribe(d -> System.out.println("MapAndFilter for: " + d));

    }
}
