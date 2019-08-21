package com.example.personsapp2.controller;

import com.example.personsapp2.handler.PositionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PositionRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PositionHandler handler) {
        return RouterFunctions
                .route(GET("/getAllPositions").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/getPosition/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)), handler::findById)
                .andRoute(POST("/createPosition").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(DELETE("/deletePosition/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::delete);
    }

}
