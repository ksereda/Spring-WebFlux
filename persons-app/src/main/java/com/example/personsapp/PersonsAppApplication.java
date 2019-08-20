package com.example.personsapp;

import com.example.personsapp.entity.Person;
import com.example.personsapp.entity.Sex;
import com.example.personsapp.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class PersonsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonsAppApplication.class, args);
	}

	// This code creates a Flux of four sample Persons objects, saves them to the DB. Then, queries all the Persons from the DB and print them to the console.
	@Bean
	CommandLineRunner run(PersonRepository personRepository) {
		return args -> {
			personRepository.deleteAll()
					.thenMany(Flux.just(
							new Person("1", Sex.MAN, "Kirill", "Sereda", "30", "programming"),
							new Person("2", Sex.MAN, "Mike", "Nikson", "28", "music"),
							new Person("3", Sex.MAN, "Oliver", "Spenser", "33", "sport"),
							new Person("4", Sex.WOMEN, "Olga", "Ivanova", "25", "movie"),
							new Person("5", Sex.WOMEN, "Anna", "Karenina", "23", "dance"),
							new Person("6", Sex.WOMEN, "Olga", "Petrova", "27", "programming")

					)
							.flatMap(personRepository::save))
					.thenMany(personRepository.findAll())
					.subscribe(System.out::println);

		};
	}

}
