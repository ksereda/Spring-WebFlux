package com.example.demoexamples;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

/**
 * ConnectableFlux
 */

public class Main8 {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("connect");
        // connect() - starts subscription manually when N-number of subscribers has subscribed

        ConnectableFlux<Integer> source = Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("Subscribed"))
                .publish();

//        ConnectableFlux<Integer> co = source.publish();

        source.subscribe(System.out::println);  // 1st subscriber
        source.subscribe(System.out::println);  // 2nd subscriber

        System.out.println("Ready");
        Thread.sleep(500);
        System.out.println("Connection");

        source.connect();    // start



        System.out.println("\nautoConnect");
        // autoConnect(n) - starts subscription automatically after N-number of subscribers

        Flux<Integer> source2 = Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("Subscribed"));

        Flux<Integer> autoConnect = source2.publish().autoConnect(2);  // as soon as 2 subscribers have signed up - let's start!

        autoConnect.subscribe(System.out::println);
        System.out.println("first subscriber");    // 1st subscriber
        Thread.sleep(500);
        System.out.println("second subscriber");   // 2nd subscriber
        autoConnect.subscribe(System.out::println);

    }
}
