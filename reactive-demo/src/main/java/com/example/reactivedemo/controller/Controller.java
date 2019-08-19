package com.example.reactivedemo.controller;

import com.example.reactivedemo.model.Entity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/start")
public class Controller {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<Entity>> load() {
        return Flux.merge(Arrays.asList(firstProducer(), secondProducer(), thirdProducer(), fourthProducer()));
    };

    private Mono<List<Entity>> firstProducer() {
        return Mono.<List<Entity>>fromCallable(() -> {
            Thread.sleep(3000);

            List<Entity> entityList = new LinkedList<>();

            Entity entity1 = new Entity();
            entity1.name = "one";

            Entity entity2 = new Entity();
            entity2.name = "two";

            Entity entity3 = new Entity();
            entity3.name = "three";

            entityList.add(entity1);
            entityList.add(entity2);
            entityList.add(entity3);

            return entityList;
        }).subscribeOn(Schedulers.elastic());
    }

    private Mono<List<Entity>> secondProducer() {
        return Mono.<List<Entity>>fromCallable(() -> {
            Thread.sleep(6000);

            List<Entity> entityList = new LinkedList<>();

            Entity entity1 = new Entity();
            entity1.name = "four";

            Entity entity2 = new Entity();
            entity2.name = "five";

            Entity entity3 = new Entity();
            entity3.name = "six";

            entityList.add(entity1);
            entityList.add(entity2);
            entityList.add(entity3);

            return entityList;
        }).subscribeOn(Schedulers.elastic());
    }

    private Mono<List<Entity>> thirdProducer() {
        return Mono.<List<Entity>>fromCallable(() -> {
            Thread.sleep(9000);

            List<Entity> entityList = new LinkedList<>();

            Entity entity1 = new Entity();
            entity1.name = "seven";

            Entity entity2 = new Entity();
            entity2.name = "eight";

            Entity entity3 = new Entity();
            entity3.name = "nine";

            entityList.add(entity1);
            entityList.add(entity2);
            entityList.add(entity3);

            return entityList;
        }).subscribeOn(Schedulers.elastic());
    }

    private Mono<List<Entity>> fourthProducer() {
        return Mono.<List<Entity>>fromCallable(() -> {
            Thread.sleep(12000);

            List<Entity> entityList = new LinkedList<>();

            Entity entity1 = new Entity();
            entity1.name = "ten";

            Entity entity2 = new Entity();
            entity2.name = "eleven";

            Entity entity3 = new Entity();
            entity3.name = "twelve";

            entityList.add(entity1);
            entityList.add(entity2);
            entityList.add(entity3);

            return entityList;
        }).subscribeOn(Schedulers.elastic());
    }

}
