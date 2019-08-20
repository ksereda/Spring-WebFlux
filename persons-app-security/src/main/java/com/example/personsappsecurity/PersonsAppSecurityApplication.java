package com.example.personsappsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class PersonsAppSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonsAppSecurityApplication.class, args);
	}

}
