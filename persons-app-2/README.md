### Router and handler

This is small example how to create reactive REST API with SpringBoot and MongoDB.

Instead `Controller` you can create `Router and Handler`.

- Handler

The `PersonHandler` is annotated with `@Component`, a generic annotation that identifies the class as being a Spring-managed bean. 
Spring will discover this component when it does its component scan and add it to the application context. 
This is not a controller, but rather a standard Spring bean that will be wired into the `PersonRouter`, defined below.

The `router` is responsible for routing HTTP requests to handler functions. 
`Handler` functions are responsible for executing business functionality and building responses.

The functions in the `PersonHandler` and `PositionHandler` return `Mono<ServerResponse>`. 
This component is a little different from the `PersonController` and `PositionController` built in the previous part (`person-app` on github), which returned Mono<Personn> and Flux<Person>. 
When building a handler function, you are responsible for building the response that will ultimately be returned to the caller. 
All methods are required to return a `Mono<ServerResponse>`, even if the body of the response contains a Flux.

Each method is passed a `ServerRequest` argument, which provides access to request parameters, such as path variables, query parameters, and, in the case of the save() method, the body of a POST or PUT.

In order to build a response body, we construct it using a `BodyBuilder`. 
The `ok()` method returns a `BodyBuilder` with an HTTP status code of `200`; it is a convenience method for status `(HttpStatus.OK)`. 
The `BodyBuilder` interface defines methods for setting the content type, content length, as well as HTTP header values. 
The `body()` method sets the contents to be returned to the caller and returns a `Mono<ServerResponse>`.

The method in this class that deserves special attention is the `save()` method. 
First, in order to deserialize the body payload to a class instance, we invoke the `bodyToMono()` method. 
This method returns a `Mono<Person>`, which is a publisher that will provide a Person instance asynchronously when it is available. 
With the `Mono<Person>` in hand, we construct the response using the `ok()` method, as usual, and then the `body()` method is implemented as follows:

    fromPublisher(person.flatMap(personService::save), Person.class)
    
The `fromPublisher()` method returns a `BodyInserter`, which the `body()` method expects, from a publisher function and the class of the object that will be published, Person.class in this case. 
The publisher function is passed the following:

    person.flatMap(personService::save)
    
    
- Router

The `Router` class is annotated with `@Configuration`, which is a Spring annotation that identifies a class as a configuration class whose methods create other Spring beans.

The `route()` method creates a bean of type `RouterFunction<ServerResponse>`. 
Router functions are responsible for translating HTTP routes (HTTP verb and URI path) into handler functions. 
For example, the first route reads: if there is a request of type GET for the URI path `/getAllPersons` and a media accept type of `APPLICATION_JSON`, then invoke the `PersonHandler`’s `findAll()` method.

    .route(GET("/getAllPersons").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
    
The `GET()` method is statically imported from the `RequestPredicates` class and returns a `RequestPredicate` instance. 
A predicate is a boolean-valued function with a test() method that evaluates the predicate and returns true or false if the predicate’s conditions are met. 
A `RequestPredicate` evaluates a `ServerRequest` to determine whether or not this route should handle the request. 
So our goal is to define the criteria under which our handler function should be called.

GET() is a convenience method for

    method(HttpMethod.GET).and(path(String Pattern))
    
This means that the `RequestPredicate` will compare the HTTP verb in the `ServerRequest` to `HttpMethod.GET` and the path to the specified URI pattern. 
We then chain `accept(MediaType.APPLICATION_JSON)` to the predicate using the `and()` method, which is a standard Predicate function that evaluates two predicates using AND boolean logic. 
The `accept()` method adds a condition to the predicate that verifies the `"Accept"` HTTP header against the provided media type. 
In the end, the `handler::findAll` method will be invoked if the following conditions are true.

The `RouterFunctions::route` method returns a `RouterFunction` that allows you to add additional routes by invoking the `addRoute()` method. 
As you can see, we leverage this capability to chain together several different routes: GET with an id request parameter, POST, and DELETE.















Вы можете также сделать класс PersonRouter


Давайте разберем что здесь происходит.

The BookRouter class is annotated with @Configuration, which is a Spring annotation that identifies a class as a configuration class whose methods create other Spring beans. In this example, the route() method creates a bean of type RouterFunction<ServerResponse>. Router functions are responsible for translating HTTP routes (HTTP verb and URI path) into handler functions. For example, the first route reads: if there is a request of type GET for the URI path /fbooks and a media accept type of APPLICATION_JSON, then invoke the BookHandler’s findAll() method.
