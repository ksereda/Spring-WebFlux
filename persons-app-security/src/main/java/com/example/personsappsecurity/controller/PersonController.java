package com.example.personsappsecurity.controller;

import com.example.personsappsecurity.entity.Person;
import com.example.personsappsecurity.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {

	private final PersonService personService;

	@GetMapping("/getAllPersons")
	public Flux<Person> getAll() {
		return personService.getAll();
	}

	@GetMapping("/getPerson/{id}")
	public Mono<Person> getById(@PathVariable("id") final String id) {
		return personService.getById(id);
	}

	@PutMapping("/updatePerson/{id}")
	public Mono updateById(@PathVariable("id") final String id, @RequestBody final Person person) {
		return personService.update(id, person);
	}

	@PostMapping("/createPerson")
	public Mono save(@RequestBody final Person person) {
		return personService.save(person);
	}

	@DeleteMapping("/deletePerson/{id}")
	public Mono delete(@PathVariable final String id) {
		return personService.delete(id);
	}

}
