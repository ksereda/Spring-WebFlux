package com.example.personsappsecurity.repository;

import com.example.personsappsecurity.entity.Position;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends ReactiveMongoRepository<Position, String> {

}
