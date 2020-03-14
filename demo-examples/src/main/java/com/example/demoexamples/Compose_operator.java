package com.example.demoexamples;

import java.util.Arrays;
import java.util.function.Function;
import reactor.core.publisher.Flux;

/**
 * Оператор transform меняет поведение потока только один раз – на этапе сборки потока.
 * Оператор compose, который выполняет то же самое преобразование потока при появлении каждого нового подписчика.
 */

public class Compose_operator {
    public static void main(String[] args) {

        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);

        Flux<String> publisher = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .compose(filterAndMap);

        publisher.subscribe(d -> System.out.println("Subscriber 1: MapAndFilter for: " + d));
        publisher.subscribe(d -> System.out.println("Subscriber 2: MapAndFilter for: " + d));

    }
}
