package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 * Оператор elapsed, как и timestamp, отмеряет интервал времени от предыдущего события.
 * Для планирования событий Reactor использует Java-класс ScheduledExecutorService, который сам по себе не гарантирует высокой точности.
 */

public class Elapsed_operator {
    public static void main(String[] args) {

        Flux.range(0, 5)
                .elapsed()
                .subscribe(e -> System.out.println("Elapsed ms: " + e.getT1() + e.getT2()));



    }
}
