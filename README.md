## Reactive Spring

### Main

I am trying to show here my understanding of the `Reactive System`.
Although I know this is a very big topic, I decided to share these small parts.
On the github, you can see several packages in which different examples are examined.

I wrote an article on topic `"Reactive Programming: Reactor and Spring WebFlux"`. 
If you are interested, you can look at the link:

    https://medium.com/@kirill.sereda/reactive-programming-reactor-%D0%B8-spring-webflux-3f779953ed45

Thanks.
_____

#### Reactive ?

Following `Spring WebFlux` documentation the `Spring Framework` uses `Reactor` internally for its own reactive support. 
`Reactor` is a `Reactive Streams` implementation that further extends the basic `Reactive Streams` `Publisher` contract with the `Flux` and `Mono` composable API types to provide declarative operations on data sequences of `0..N` and `0..1`. 
On the server-side Spring supports annotation based and functional programming models. 
Annotation model use @Controller and the other annotations supported also with Spring MVC. 
Reactive controller will be very similar to standard REST controller for synchronous services instead of it uses Flux, Mono and Publisher objects.

#### Why using Reactive types?

Reactive types are not intended to allow you to process your requests or data faster, in fact they will introduce a small overhead compared to regular blocking processing. 
Their strength lies in their capacity to serve more request concurrently, and to handle operations with latency, such as requesting data from a remote server, more efficiently. 
They allow you to provide a better quality of service and a predictable capacity planning by dealing natively with time and latency without consuming more resources. 
Unlike traditional processing that blocks the current thread while waiting a result, a Reactive API that waits costs nothing, requests only the amount of data it is able to process and bring new capabilities since it deals with stream of data, not only with individual elements one by one.

#### Reactive APIs
Reactive APIs such as Reactor also provide operators like Java 8 Stream, but they work more generally with any stream sequence (not just Collections) and allow to define a pipeline of transforming operations that will apply to the data passing through it thanks to a handy fluent API and using lambdas. 
They are designed to handle both synchronous or asynchronous operations, and allow you to buffer, merge, concatenate, or apply a wide range of transformations to your data.

There is 2 different web stack in Spring 5 - `spring-web-mvc` and `spring-web-reactive.` 
Each module is optional.
Applications can use one or the other module or, in some cases, both?—? 
for example, Spring MVC controllers with the reactive `WebClient`.
 
#### What is Spring WebFlux ?

To support reactive programming and the creation of reactive systems, the Spring Boot team created a whole new web stack called `Spring WebFlux`. 
This new web stack supports annotated controllers, functional endpoints, `WebClient` (analogous to RestTemplate in Spring Web MVC), WebSockets and a lot more.
 
`Spring WebFlux` framework is part of Spring 5 and provides reactive programming support for web applications.

`Spring WebFlux` internally uses `Project Reactor` and its publisher implementations – `Flux` and `Mono`.

`Mono` is the Reactive equivalent of CompletableFuture type, and allow to provide a consistent API for handling single and multiple elements in a Reactive way.

If you have a deeper look to `Flux` and `Mono`, you will notice these types implement the Publisher interface from the Reactive Streams specification.


#### Reactive Streams

`Reactive Streams` is an initiative to provide a standard for asynchronous stream processing with non-blocking back pressure, as can be read on the website. 
This means that a source can send data to a destination without overwhelming the destination with too much data. The destination will tell the source how much data it can handle. 
This way, resources are used more efficiently.

The specification consists of the following items:

- `Publisher:` The publisher is the source that will send the data to one or more subscribers.
- `Subscriber:` A subscriber will subscribe itself to a publisher, will indicate how much data the publisher may send and will process the data.
- `Subscription:` On the publisher side, a subscription will be created, which will be shared with a subscriber.
- `Processor:` A processor can be used to sit between a publisher and a subscriber, this way a transformation of the data can take place.

The `Reactive Streams` specification is a standard and since Java 9, it is included in Java with the Flow API. 
Take a closer look at the Reactive Streams specification and the Java 9 Flow API. As you will notice, the interfaces for `Publisher, Subscriber, Subscription, and Processor` are identical.


### Errors in Spring WebFlux

- Handling Errors at a Functional Level

  There are two key operators built into the Mono and Flux APIs to handle errors at a functional level.
  
    - `onErrorReturn`
    
        It return a static default value whenever an error occurs.
    
    - `onErrorResume`
    
    - `DoOnError `
    
        It will only perform side effects and assuming the findById are will return a Mono.Error() if it fails something like this should work.
  
- Handling Errors at a Global Level
    - Customize the Global Error Response Attributes
    - Implement the Global Error Handler



Processing examples if no data

    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<Bucket>> getBucketById(@PathVariable(value = "id") String bucketId) {
        return bucketRepository.findById(bucketId)
                .map(saveBucket -> ResponseEntity.ok(saveBucket))
                .defaultIfEmpty(ResponseEntity.notFound().build()); 
                .switchIfEmpty(Mono.error(new BucketNotFoundException("Data not found")));
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

