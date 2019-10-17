package com.ritxar.gradleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class GradleProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradleProjectApplication.class, args);
	}

}

@Configuration
class MyConfig {
	@Bean
	PersonFetcher personFetcher() {
		return new PersonFetcher();
	}

	@Bean
	PersonPrinter personPrinter() {
		return new PersonPrinter();
	}
}

class PersonPrinter {
	@Autowired private PersonFetcher personFetcher;

	String print() {
		return this.personFetcher.fetchPeople().stream()
				.map(person -> person.name)
				.collect(Collectors.joining("\n"));
	}
}

class PersonFetcher {
	List<Person> fetchPeople() {
		return Arrays.asList(new Person("marcin", "g"),
				new Person("john", "d"));
	}
}

class Person {
	String name, surname;

	public Person (String name, String surname) {
		this.name = name;
		this.surname = surname;
	}
}
