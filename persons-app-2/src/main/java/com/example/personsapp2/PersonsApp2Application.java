package com.example.personsapp2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class PersonsApp2Application {

	public static void main(String[] args) {
		SpringApplication.run(PersonsApp2Application.class, args);
	}

}
