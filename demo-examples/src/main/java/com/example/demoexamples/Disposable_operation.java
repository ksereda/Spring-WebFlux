package com.example.demoexamples;

import java.time.Duration;


import reactor.core.Disposable;
import reactor.core.publisher.Flux;

/**
 * Disposable
 *         метод interval позволяет генерировать события с заданной периодичностью (в данном случае каждые 50 мс). Генерируемый им поток данных не имеет конца.
 *         Ждем некоторое время,чтобы получить несколько событий.
 *         Вызов метода dispose, который отменяет подписку.
 */

public class Disposable_operation {
    public static void main(String[] args) throws InterruptedException {

        Disposable disposableResult = Flux.interval(Duration.ofMillis(50))
                .subscribe(System.out::println);

        Thread.sleep(200);
        disposableResult.dispose();

    }
}
