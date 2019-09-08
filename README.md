## Reactive Spring

### Main

I am trying to show here my understanding of the `Reactive System`.
Although I know this is a very big topic, I decided to share these small parts.
On the github, you can see several packages in which different examples are examined.

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



Примеры обработки в случае если данных нет

    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<Bucket>> getBucketById(@PathVariable(value = "id") String bucketId) {
        return bucketRepository.findById(bucketId)
                .map(saveBucket -> ResponseEntity.ok(saveBucket))
                .defaultIfEmpty(ResponseEntity.notFound().build()); 
                .switchIfEmpty(Mono.error(new BucketNotFoundException("Data not found")));
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


### WebClient

WebClient, представленный в Spring 5, является неблокирующим клиентом с поддержкой Reactive Streams (реактивный HTTP-клиент).
WebClient является частью реактивной веб-среды (фреймворка) Spring 5 под названием Spring WebFlux. 
Для работы с ним надо включить модуль spring-webflux в проект.
вам нужна Spring Boot версии 2.xx для использования модуля Spring WebFlux.

Он был создан как часть модуля Spring Web Reactive и будет заменять классический RestTemplate в этих сценариях. Новый клиент является реактивным, неблокирующим решением, работающим по протоколу HTTP / 1.1.

Создание:

WebCLient можно создать при помощи create

    WebClient webClient = WebClient.create("https://api.github.com");

Или при помощи builder

        public WebClientService() {
            this.webClient = WebClient.builder()
                    .baseUrl(API_BASE_URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, API_MIME_TYPE)
                    .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                    .build();
        }
        

В нашем примере мы используем WebClient для получения данных из базы данных через первый сервис (gallery-service) в реактивной среде.

    public Flux<Bucket> getDataByWebClient() {
        return webClient
                .get()
                .uri("/stream/buckets/delay")
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Bucket.class));
    }
    
- мы можем указать какой именно запрос мы отправляем параметром
    
        .get()
        .post()
        .put()
        .delete()
        .options()
        .head()
        .patch()
        
- предоставление URL мы передаем через 

        .uri
        
для создания URI запроса вы можете также использовать URIBuilder

    .uri(uriBuilder -> uriBuilder.path("/user/repos")
                        .queryParam("sort", "updated")
                        .queryParam("direction", "desc")
                        .build())
                        
В итоге ваш финальный клиент будет выглядеть подобным образом

    public Flux<MyRepo> listRepositories(String username, String token) {
         return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/user/repos")
                        .queryParam("sort", "updated")
                        .queryParam("direction", "desc")
                        .build())
                .header("Authorization", "Basic " + Base64Utils
                        .encodeToString((username + ":" + token).getBytes(UTF_8)))
                .retrieve()
                .bodyToFlux(MyRepo.class);
    }

        
- мы можем установить тело запроса (Если шлем POST запрос)

если мы хотим установить тело запроса - есть два доступных способа:
 
 Первый способ - заполнить его BodyInserter или делегировать эту работу Publisher 

  .body(BodyInserters.fromPublisher(Mono.just("data")), String.class);
 
  
  BodyInserter - это интерфейс, отвечающий за заполнение тела ReactiveHttpOutputMessage.
  BodyInserters Класс содержит методы , чтобы создать BodyInserterиз Object, Publisher, Resource, FormData, и MultipartDataт.д. 
  
  Publisher - это реактивный компонент, отвечающий за предоставление потенциально неограниченного числа последовательных элементов.
  
 Второй способ - это метод body , который является ярлыком для исходного метода body (BodyInserter insertter) .
 
  с помощью одного объекта
  
   .body(BodyInserters.fromObject("data"));
   
   c помощью MultiValueMap
   
   LinkedMultiValueMap map = new LinkedMultiValueMap();
   
   map.add("key1", "value1");
   map.add("key2", "value2");
   
   BodyInserter<MultiValueMap, ClientHttpRequest> inserter2
    = BodyInserters.fromMultipartData(map);
    
    
Если у вас есть тело запроса в форме a Mono или a Flux, то вы можете напрямую передать его body()методу в WebClient.
Если у вас есть действительное значение вместо Publisher( Flux/ Mono), вы можете использовать syncBody().



- мы можем установить заголовки, куки, приемлемые типы носителей.

существует дополнительная поддержка наиболее часто используемых заголовков, таких как __ «If-None-Match», «If-Modified-Since», «Accept», «Accept-Charset».

     .header(HttpHeaders.CONTENT__TYPE, MediaType.APPLICATION__JSON__VALUE)
    .accept(MediaType.APPLICATION__JSON, MediaType.APPLICATION__XML)
    .acceptCharset(Charset.forName("UTF-8"))
    .ifNoneMatch("** ")
    .ifModifiedSince(ZonedDateTime.now())




Фильтры

WebClient поддерживает фильтрацию запросов с использованием ExchangeFilterFunction. Вы можете использовать функции фильтра для перехвата и изменения запроса любым способом. Например, вы можете использовать функцию фильтра для добавления Authorizationзаголовка к каждому запросу или для регистрации деталей каждого запроса.

Он принимает два аргумента -
   
   ClientRequestи
   Следующее ExchangeFilterFunctionв цепочке фильтров.
   
Например Добавление базовой аутентификации с использованием функции фильтра.
вы можете добавить эту логику в функцию фильтра при создании WebClient.

    WebClient webClient = WebClient.builder()
            .baseUrl(MY_REPO_API_BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, GITHUB_V3_MIME_TYPE)
            .filter(ExchangeFilterFunctions
                    .basicAuthentication(username, token))
            .build();   

Теперь вам не нужно добавлять Authorizationзаголовок в каждом запросе. Функция фильтра будет перехватывать каждый запрос WebClient и добавлять этот заголовок.



Получение ответа

Для этого есть методы exchange или retrieve

retrieve - предоставляет кратчайший путь для непосредственного извлечения тела.
 retrieve()Метод является самым простым способом получить тело ответа. 
 Однако, если вы хотите иметь больше контроля над ответом, вы можете использовать exchange()метод, который имеет доступ ко всему, ClientResponseвключая все заголовки и тело


exchange - предоставляет ClientResponse вместе со своим статусом, заголовки



Для тетсирования представлен WebTestClient.
У него очень похожий API на WebClient , и он делегирует большую часть работы внутреннему экземпляру WebClient , ориентируясь главным образом на предоставление тестового контекста. Класс DefaultWebTestClient представляет собой единую реализацию интерфейса.
Клиент для тестирования может быть привязан к реальному серверу или работать с конкретными контроллерами или функциями. 

WebTestClient testClient = WebTestClient
  .bindToServer()
  .baseUrl("http://localhost:8080")
  .build();
  
  
  
  
  
Какое главное отличие WebClient от RestTemplate ?

Основным отличием является то, что RestTemplate продолжает использовать API сервлетов Java и выполняет синхронную блокировку. Это означает, что вызов, выполненный с использованием RestTemplate, должен ждать, пока ответ не вернется, чтобы продолжить.
С другой стороны, поскольку WebClient является асинхронным, вызову rest не нужно ждать ответа. Вместо этого, когда есть ответ, будет предоставлено уведомление.




Обработка ошибок WebClient

retrieve() Метод , в WebClient бросает WebClientResponseExceptionвсякий раз , когда ответ с кодом состояния 4xx или 5xx получен.

    public Flux<MyRepo> listGithubRepositories() {
         return webClient.get()
                .uri("/user/repos?sort={sortField}&direction={sortDirection}", 
                         "updated", "desc")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                    Mono.error(new MyCustomClientException())
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                    Mono.error(new MyCustomServerException())
                )
                .bodyToFlux(MyRepo.class);
    
    }
    
exchange() метод не генерирует исключения в случае ответов 4xx или 5xx. Вам необходимо самостоятельно проверить коды состояния и обрабатывать их так, как вы хотите.


Вы также можете проверить статусы состояний и передать какое-то свое кастомное выполнение для их реализаций

        public Flux<Bucket> getDataByWebClient() {
            return webClient
                    .get()
                    .uri("/getAll")
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                            Mono.error(new RuntimeException("4xx"))
                    )
                    .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                            Mono.error(new RuntimeException("5xx"))
                    )
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse ->
                            Mono.error(new MyCustomServerException())
                    )
                    .onStatus(HttpStatus::isError, clientResponse ->
                            Mono.error(new MyCustomServerException())
                    )
                    .bodyToFlux(Bucket.class);
        }
        
