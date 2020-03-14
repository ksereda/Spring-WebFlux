package com.example.demoexamples;

import java.util.function.Consumer;


import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * В отличие от publishOn, оператор subscribeOn позволяет изменить рабочий поток выполнения, в котором выполняется часть цепочки, относящаяся к подписке.
 *
 *
 */

public class SubscribeOn_operator {
    public static void main(String[] args) {

        Consumer<Integer> consumer = s -> System.out.println(s + " : " + Thread.currentThread().getName());

        Flux.range(1, 5)
                .subscribeOn(Schedulers.newElastic("subscribeOn: "))
                .doOnNext(consumer)
                .map(i -> i + 1)
                .publishOn(Schedulers.newElastic("publishOn: "))
                .doOnNext(consumer)
                .map(s -> s + 5)
                .subscribe(System.out::println);

    }
}
