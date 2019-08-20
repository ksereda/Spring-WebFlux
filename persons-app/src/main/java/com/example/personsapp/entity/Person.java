package com.example.personsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@Document
public class Person {

	@Id
	private String id;

	private Sex sex;
	private String firstName;
	private String lastName;
	private int age;
	private String interests;

}
