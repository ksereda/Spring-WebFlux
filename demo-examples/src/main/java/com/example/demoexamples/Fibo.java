package com.example.demoexamples;

import java.time.Duration;


import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class Fibo {
    public static void main(String[] args) {

        Flux.generate(
                () -> Tuples.of(0L, 1L),
                (state, sink) -> {
                    System.out.println("generated value: " + state.getT2());
                    sink.next(state.getT2());
                    long newValue = state.getT1() + state.getT2();
                    return Tuples.of(state.getT2(), newValue);
                })
                .delayElements(Duration.ofMillis(1))
                .take(4)
                .subscribe(e -> System.out.println("onNext: " + e));

    }
}
