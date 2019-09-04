package com.example.demoexamples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class Main5 {
    public static void main(String[] args) throws InterruptedException {


        System.out.println("Example 1:");
        // Block the current thread until we get the object
        String result = Mono.just("one")
                .block();
        System.out.println(result);



        System.out.println("\nExample 2:");
        // Block the current thread until we receive and process the data
        String result2 = Mono.just("one")
                .map(String::toUpperCase)
                .block();
        System.out.println(result2);



        System.out.println("\nExample 3:");
        // We filter all users and will get the user with the name "name1"
        Flux.just(new User("Name1", 20), new User("name2", 25), new User("name3", 30))
                .filter(s -> s.getName().equals("name1"))
                .subscribe(s -> System.out.println("Value: " + s.getName()));



        System.out.println("\nExample 4:");
        // Convert User Type to String
        Flux.just(new User("Name1", 20), new User("name2", 25), new User("name3", 30))
                .map(User::getName)
                .subscribe(System.out::println);



        System.out.println("\nExample 5:");
        // We expect data to be received for 1 second and only after that we process events
        // This code will not output anything, because when the main thread of execution completes, our data collection stops and the program terminates
        Flux.just(new User("Name1", 20), new User("name2", 25), new User("name3", 30))
            .delayElements(Duration.ofSeconds(1))
                .subscribe(System.out::println);



        System.out.println("\nExample 6:");
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux.just(new User("Name1", 20), new User("name2", 25), new User("name3", 30))

        // We start Flux with operation after one second
        //Then we set the reset counter at the end
                .delayElements(Duration.ofSeconds(1))
                .doOnComplete(countDownLatch::countDown)
                .subscribe(System.out::println);    // output every second

        // Waiting for counter reset
        countDownLatch.await();

    }

}
