package com.example.demoexamples;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

/**
 * Implementing your own subscriber
 * Let's create our own subscriber to understand how it is built inside.
 *
 * Our subscriber must keep a link to a copy of the Subscription subscription linking the publisher Publisher and our Subscriber subscriber.
 * Here in the onSubscribe() method we specify Subscription - it is responsible for backpressure (i.e. we can specify how much data we want to receive from the publisher - no more, i.e. how much data we can process!).
 * After subscribing, our Subscriber subscriber is informed by the onSubscribe() callback. Here we save our subscription and send the publisher a request with the initial requirements (how much data we are ready to accept). Without such a request the manufacturer will not send data and processing of elements will never begin at all.
 * In the onNext() callback, register the received data and request the next element.
 * And it the end we generate a certain stream and subscribe to it.
 */

public class MyOwnSubscriver {
    public static void main(String[] args) {

        Subscriber<String> subscriber = new Subscriber<String>() {
            volatile Subscription subscription;

            public void onSubscribe(Subscription s) {
                subscription = s;
                System.out.println("initial request for 1 element");
                subscription.request(1);
            }

            public void onNext(String s) {
                System.out.println("onNext: {}" + s);
                System.out.println("requesting 1 more element");
                subscription.request(1);
            }

            public void onComplete() {
                System.out.println("onComplete");
            }

            public void onError(Throwable t) {
                System.out.println("onError: {}" + t.getMessage());
            }

        };

        Flux.just("One", "Two", "Java")
                .subscribe(subscriber);

    }
}
