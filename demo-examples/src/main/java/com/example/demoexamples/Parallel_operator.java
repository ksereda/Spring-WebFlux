package com.example.demoexamples;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Он позволяет разбить один поток данных на несколько подпотоков и распределить элементы между ними.
 *
 * После применения оператора parallel мы начинаем работать с другим типом Flux – ParallelFlux.
 * ParallelFlux – это абстракция группы экземпляров Flux, между которыми распределяются элементы из исходного потока Flux.
 *
 * Затем, применив оператор runOn, можно применить publishOn к внутренним экземплярам Flux и распределить работу по обработке элементов между несколькими рабочими потоками.
 */

public class Parallel_operator {
    public static void main(String[] args) {

        Flux.range(0, 100)
                .parallel()
                .runOn(Schedulers.parallel())
                .map(s -> s + 1)
                .filter(f -> f <= 10)
                .subscribe(System.out::println);

    }
}
