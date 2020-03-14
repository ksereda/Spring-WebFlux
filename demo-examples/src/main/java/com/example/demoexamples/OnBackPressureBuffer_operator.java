package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 * оператор onBackPressureBuffer запрашивает неограниченный объем данных и передает получаемые элементы в нисходящий поток,
 * а если потребитель нисходящего потока не справляется с нагрузкой, он сохраняет элементы в очереди.
 * Оператор onBackPressureBuffer имеет множество перегруженных версий и предлагает большое число параметров настройки для управления поведением
 */

public class OnBackPressureBuffer_operator {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .take(5)
                .onBackpressureBuffer(5)
                .map(index -> index + "_new")
                .subscribe(System.out::println);

    }
}
