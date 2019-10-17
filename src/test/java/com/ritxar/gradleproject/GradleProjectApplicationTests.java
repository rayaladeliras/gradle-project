package com.ritxar.gradleproject;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class GradleProjectApplicationTests {

	@Autowired PersonService personService;

	@Test
	public void should_get_two_people() {
		List<Person> people = this.personService.people();
		BDDAssertions.then(people).hasSize(2);
	}

	@Configuration
	static class PersonTestConfiguration extends MyConfig {
		@Override
		PersonClient personClient () {
			return id -> new Person ("m", "["+id+"]");
		}
	}
}
