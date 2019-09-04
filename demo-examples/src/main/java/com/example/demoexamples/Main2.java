package com.example.demoexamples;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Example 1:");
        // Let's create a simple Flux that will immediately send us some messages.
        // Subscribing to events is simple: we call the subscribe method and pass the lambda there, which will be called for each element in the stream
        Flux.just("1", "2", "3")
                .subscribe(value -> System.out.println("Value: " + value));


        System.out.println("\nExample 2:");
        // In case of an exception, the onError method is called during processing of elements.
        // The handler for it can be set by the second parameter .subscribe()
        Flux.just(1, 2, 3, 4)
                .subscribe(value -> {
                    if (value > 3) {
                        throw new IllegalArgumentException("value is too big");
                    }
                    System.out.println("Value: " + value);
                }, error -> System.out.println("Error occurred: " + error.getMessage()));


        System.out.println("\nExample 3:");
        // If successful, the .onComplete() handler will execute.
        // It can be set by the third parameter
        Flux.just(1, 2, 3, 4)
                .subscribe(value -> System.out.println("Value: " + value),
                        error -> {},
                        () -> System.out.println("Successfull"));


        System.out.println("\nExample 4:");
        Flux.just(1, 2, 3, 4)
                .filter(value -> value % 2 == 0)
                .map(value -> value * 3)
                .subscribe(value -> System.out.println("Value: " + value));


        System.out.println("\nExample 5:");
        // You can block Flux by calling the .collect() method to get all the completion data
        Mono<List<Integer>> listMono = Flux.just(1, 2, 3, 4, 5 ,6)
                .filter(value -> value % 2 != 0)
                .collectList();

        System.out.println(listMono.block());


        System.out.println("\nExample 6:");
        // You can unsubscribe using the Disposable object
        // You will receive only: 1, 2
        Disposable disposable = Flux.just(1, 2, 3, 4, 5, 6, 7, 8)
                .delayElements(Duration.ofSeconds(3))
                .subscribe(value -> System.out.println("Value: " + value));

        Thread.sleep(7000);
        disposable.dispose();
        System.out.println("Cancelling subscription");


        System.out.println("\nExample 7:");
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .skip(3)
                .limitRequest(5)
                .subscribe(value -> System.out.println("Value: " + value));

    }
}
