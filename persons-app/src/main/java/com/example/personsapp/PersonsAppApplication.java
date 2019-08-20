package com.example.personsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class PersonsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonsAppApplication.class, args);
	}

}
