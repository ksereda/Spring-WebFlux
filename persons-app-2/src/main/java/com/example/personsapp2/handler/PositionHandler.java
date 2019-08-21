package com.example.personsapp2.handler;

import com.example.personsapp2.entity.Position;
import com.example.personsapp2.repository.PositionRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class PositionHandler {

    private final PositionRepository positionRepository;

    public PositionHandler(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionRepository.findById(id), Position.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionRepository.findAll(), Position.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        final Mono<Position> position = request.bodyToMono(Position.class);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(position.flatMap(positionRepository::save), Position.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionRepository.deleteById(id), Void.class);
    }

}
