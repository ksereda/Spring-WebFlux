package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 * оператор onBackPressureDrop также запрашивает неограниченный объем данных (Integer.MAX_VALUE) и передает получаемые элементы в нисходящий поток.
 * Если потребитель нисходящего потока не справляется с нагрузкой, оператор отбрасывает элементы.
 * Такие отбрасываемые элементы можно обрабатывать с помощью дополнительного обработчика
 */

public class OnBackPressureDrop_operator {
    public static void main(String[] args) {

        Flux.range(1, 10)
                .take(5)
                .onBackpressureDrop()
                .map(index -> index + "_new")
                .subscribe(System.out::println);

    }
}
