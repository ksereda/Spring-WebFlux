package com.example.demoexamples;

import reactor.core.publisher.Flux;

/**
 * Оператор limitRequest(n) позволяет ограничить требование (общее количество запрашиваемых элементов) со стороны потребителя.
 * Например, вызов limitRequest(10) гарантирует, что производитель не получит запроса, требующего послать больше 10 элементов.
 * После отправки 10 элементов оператор благополучно закроет по- ток данных.
 */

public class LimitRequest_operator {
    public static void main(String[] args) {

        // В этом примере не смотря на то, что мы явно хотим получить все 10 элементов с помощью take(10), мы получим только 5,
        // т.к. издатель получит запрос от нас о том, что мы запросили только 5 элементов - limitRequest(5)
        System.out.println("Example 1:");
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .limitRequest(5)
                .take(10)
                .subscribe(System.out::println);

        // Здесь мы ограничиваемся 5-ю элементами в запросе и получим из этих 5 только 3 элемента
        System.out.println("\nExample 2:");
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .limitRequest(5)
                .take(3)
                .subscribe(System.out::println);

    }
}
