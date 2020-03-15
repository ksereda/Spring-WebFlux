package com.example.demoexamples;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ContextAPI {
    public static void main(String[] args) {

        // ThreadLocal
//        ThreadLocal<Map<Object, Object>> threadLocal = new ThreadLocal<>();
//        threadLocal.set(new HashMap<>());
//
//        Flux.range(0, 5)
//                .doOnNext(k -> threadLocal
//                                .get()
//                                .put(k, new Random(k).nextGaussian())
//                )
//                .publishOn(Schedulers.parallel())
//                .map(k -> threadLocal
//                        .get()
//                        .get(k))
//                .blockLast();


        // Context
        Flux.range(0, 5)
                .flatMap(k ->
                        Mono.subscriberContext().doOnNext(context -> {
                            Map<Object, Object> map = context.get("randoms");
                            map.put(k, new Random(k).nextGaussian());
                        })
                          .thenReturn(k)
                )
                .publishOn(Schedulers.parallel())
                .flatMap(k ->
                        Mono.subscriberContext()
                                .map(context -> {
                                    Map<Object, Object> map = context.get("randoms");
                                    return map.get(k);
                                })
                )
                .subscriberContext(context ->
                        context.put("randoms", new HashMap()) )
                .blockLast();


    }
}
