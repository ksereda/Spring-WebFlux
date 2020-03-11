package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 *   Подсчитаем сумму целых чисел от 1 до 10
 *   Оператор reduce возвращает только один элемент, представляющий окончательный результат.
 */

public class Reduce_operator {
    public static void main(String[] args) {

        Flux.range(1, 5)
//                .reduce(0, (acc, elem) -> acc + elem)
                .reduce(0, Integer::sum)
                .subscribe(result -> System.out.println("Result: " + result));

    }
}
