package com.example.personsapp.controller;

import com.example.personsapp.entity.Person;
import com.example.personsapp.entity.Position;
import com.example.personsapp.entity.Sex;
import com.example.personsapp.exception.PositionNotFoundException;
import com.example.personsapp.payload.ErrorResponse;
import com.example.personsapp.repository.PersonRepository;
import com.example.personsapp.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/positions")
    public Flux<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @PostMapping("/createPosition")
    public Mono<Position> createTweets(@Valid @RequestBody Position position) {
        return positionRepository.save(position);
    }

    @GetMapping("/position/{id}")
    public Mono<ResponseEntity<Position>> getPositionById(@PathVariable(value = "id") String positionId) {
        return positionRepository.findById(positionId)
                .map(savedPosition -> ResponseEntity.ok(savedPosition))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/position/{id}")
    public Mono<ResponseEntity<Position>> updatePosition(@PathVariable(value = "id") String positionId,
                                                   @Valid @RequestBody Position position) {
        return positionRepository.findById(positionId)
                .flatMap(existingPosition -> {
                    existingPosition.setPositionName(position.getPositionName());
                    return positionRepository.save(existingPosition);
                })
                .map(updatePosition -> new ResponseEntity<>(updatePosition, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/position/{id}")
    public Mono<ResponseEntity<Void>> deletePosition(@PathVariable(value = "id") String positionId) {

        return positionRepository.findById(positionId)
                .flatMap(existingPosition ->
                        positionRepository.delete(existingPosition)
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Positions are Sent to the client as Server Sent Events
    @GetMapping(value = "/stream/positions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Position> streamAllPositions() {
        return positionRepository.findAll();
    }

    // Get default value every 1 second
    @GetMapping(value = "/stream/persons", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> emitPersons() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(val -> new Person( "" + val, Sex.MAN, "default", "default", "", "default"));
    }

    // Exception Handling Examples (These can be put into a @ControllerAdvice to handle exceptions globally)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity handleDuplicateKeyException(DuplicateKeyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("A position with the same position name already exists"));
    }

    @ExceptionHandler(PositionNotFoundException.class)
    public ResponseEntity handlePositionNotFoundException(PositionNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}
