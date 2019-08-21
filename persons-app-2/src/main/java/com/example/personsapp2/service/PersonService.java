package com.example.personsapp2.service;

import com.example.personsapp2.entity.Person;
import com.example.personsapp2.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class PersonService {

	private final PersonRepository personRepository;

	public Flux<Person> getAll() {
		return personRepository.findAll().switchIfEmpty(Flux.empty());
	}

	public Mono<Person> getById(final String id) {
		return personRepository.findById(id);
	}

	public Mono update(final String id, final Person person) {
		return personRepository.save(person);
	}

	public Mono save(final Person person) {
		return personRepository.save(person);
	}

	public Mono delete(final String id) {
		final Mono<Person> dbPerson = getById(id);
		if (Objects.isNull(dbPerson)) {
			return Mono.empty();
		}
		return getById(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull).flatMap(personToBeDeleted -> personRepository
				.delete(personToBeDeleted).then(Mono.just(personToBeDeleted)));
	}
}
