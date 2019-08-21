package com.example.personsapp2.controller;

import com.example.personsapp2.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PersonRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PersonHandler handler) {
        return RouterFunctions
                .route(GET("/getAllPersons").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/getPerson/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)), handler::findById)
                .andRoute(POST("/createPerson").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(DELETE("/deletePerson/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::delete);
    }

}
