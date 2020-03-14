package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 * Оператор limitRate(n) разбивает запрошенный снизу объем данных на пакеты с размером не больше n.
 * С помощью этого оператора можно защитить уязвимого производителя от запроса необоснованно большого объема данных.
 *
 * Т.е. здесь мы получим 5 пакетов по 2 значения в каждом, например: 1-2, 3-4, 5-6, 7-8, 9-10
 */

public class LimitRate_operator {
    public static void main(String[] args) {

        Flux.range(1, 10)
                .limitRate(5)
                .subscribe(System.out::println);

    }
}