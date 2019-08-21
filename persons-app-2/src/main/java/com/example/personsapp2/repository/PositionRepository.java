package com.example.personsapp2.repository;

import com.example.personsapp2.entity.Position;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends ReactiveMongoRepository<Position, String> {

}
