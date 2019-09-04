package com.example.demoexamples;

import org.springframework.web.multipart.MultipartException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.sql.SQLException;

public class Main4 {

    private int testNumbers(int value) {
        if (value > 2) {
            throw new IllegalArgumentException("value is too high!");
        }
        return value;
    }

    private Flux<Integer> checkOnErrorMethod() {
        Flux.just(1, 2, 3)
                .map(this::testNumbers)
                .onErrorReturn(100)
                .subscribe(value -> System.out.println("Value: " + value));

        return null;

    }

    private Flux<Integer> checkOnErrorMethod2() {
        Flux.just(1, 2, 3)
                .map(this::testNumbers)
                .onErrorResume(error -> Flux.just(4, 5, 6))
                .subscribe(value -> System.out.println("Value: " + value));

        return null;

    }

    private Flux<Integer> checkOnErrorMethodMyException() {
        Flux.just(1, 2, 3)
                .map(this::testNumbers)
                .onErrorMap(error -> new SQLException("this is SQLException"))
                .subscribe(value -> System.out.println("Value: " + value),
                        error -> System.out.println("Error: " + error.getClass().getSimpleName() + ". Message: " + error.getMessage()));

        return null;

    }

    private Flux<Integer> checkOnErrorMethodTryCatchFinally() {

        Flux.just(1, 2, 3)
                .map(this::testNumbers)
                .doFinally(signalType -> {
                    if (signalType == SignalType.ON_ERROR) {
                        System.out.println("Error signal");
                    } else if (signalType == SignalType.CANCEL) {
                        System.out.println("Cancel signal");
                    }
                })
                .subscribe(value -> System.out.println("Value: " + value),
                        error -> System.out.println("Error: " + error.getMessage()));

        return null;

    }

    private Flux<Integer> checkOnErrorMethodRetry() {
        Flux.just(1, 2, 3)
                .map(this::testNumbers)
                .retry(2)
                .subscribe(value -> System.out.println("Value: " + value),
                        error -> System.out.println("Error: " + error.getMessage()));

        return null;

    }

    public static void main(String[] args) {

        System.out.println("Example 1:");
        // Exceptions are terminal. The thread immediately completes its work
        Flux.just(1, 2, 3)
                .subscribe(value -> {
                    if (value > 2) {
                        throw new IllegalArgumentException("value is too high!");
                    }
                    System.out.println("Value: " + value);
                }, error -> System.out.println("Error: " + error.getMessage()));



        System.out.println("\nExample 2:");
        // You can return the default value in case of a method execution error
        Main4 main4 = new Main4();
        System.out.println(main4.checkOnErrorMethod());



        System.out.println("\nExample 3:");
        // In case of an error, you can recover and continue working
        System.out.println(main4.checkOnErrorMethod2());



        System.out.println("\nExample 4:");
        // In case of an error, you can throw your own exception
        System.out.println(main4.checkOnErrorMethodMyException());



        System.out.println("\nExample 5:");
        // Replace try-catch-finally for reactive flow - exit if an error occurs
        System.out.println(main4.checkOnErrorMethodTryCatchFinally());



        System.out.println("\nExample 6:");
        // In case of an error, you can try again
        System.out.println(main4.checkOnErrorMethodRetry());


    }
}
