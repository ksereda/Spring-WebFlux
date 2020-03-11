package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 * Сначала запрашивается четыре элемента, а потом подписка сразу же отменяется, то есть другие элементы вообще не должны генерироваться (блок subscription).
 * мы не получили сигнал onComplete, потому что подпис- чик отменил подписку до завершения потока данных.
 */

public class Backpressure_exmaple {
    public static void main(String[] args) {

        Flux.range(1, 100)
                .subscribe(
                        System.out::println,
                        err -> {
                        },
                        () -> System.out.println("Done"),
                        subscription -> {
                            subscription.request(4);
                            subscription.cancel();
                        }
                );

    }
}
