package com.example.demoexamples;

import java.time.Duration;


import reactor.core.publisher.Flux;

public class Share_operator {
    public static void main(String[] args) throws InterruptedException {

        Flux<Integer> source = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(100))
                .doOnSubscribe(s ->
                        System.out.println("new subscription for the cold publisher")
                );

        Flux<Integer> cachedSource = source.share();
        cachedSource.subscribe(e ->  System.out.println("[S 1] onNext: " + e));
        Thread.sleep(400);
        cachedSource.subscribe(e ->  System.out.println("[S 2] onNext: " + e));

    }
}
